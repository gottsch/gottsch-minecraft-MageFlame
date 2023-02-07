/*
 * This file is part of  Mage Flame.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
 *
 * Mage Flame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mage Flame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Mage Flame.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.forge.mageflame.core.util;

import java.util.Collection;
import java.util.Map.Entry;

import mod.gottsch.forge.mageflame.core.block.ISummonFlameBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;

/**
 * 
 * @author Mark Gottschling Feb 6, 2023
 *
 */
public class LevelUtil {
	/**
	 * Convenience method
	 * @param level
	 * @param pos
	 * @param state
	 * @param flag
	 * @return
	 */
	public static boolean setBlockForced(Level level, BlockPos pos, BlockState state, int flag) {
		return setBlockForced(level, pos, state, flag, 512);  
	}

	/**
	 * 
	 * @param level
	 * @param pos
	 * @param state
	 * @param flag
	 * @param value512
	 * @return
	 */
	private static boolean setBlockForced(Level level, BlockPos pos, BlockState state, int flag, int value512) {
		if (level.isOutsideBuildHeight(pos)) {
			return false;
		} else if (!level.isClientSide && level.isDebug()) {
			return false;
		} else {
			LevelChunk levelChunk = level.getChunkAt(pos);

			pos = pos.immutable(); // Forge - prevent mutable BlockPos leaks
			net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;
			if (level.captureBlockSnapshots && !level.isClientSide) {
				blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, pos, flag);
				level.capturedBlockSnapshots.add(blockSnapshot);
			}

			BlockState oldState = level.getBlockState(pos);
			int oldLight = oldState.getLightEmission(level, pos);
			int oldOpacity = oldState.getLightBlock(level, pos);

			BlockState previoiusChunkBlockState = setBlockStateForced(levelChunk, pos, state, (flag & 64) != 0);
			if (previoiusChunkBlockState == null) {
				if (blockSnapshot != null) level.capturedBlockSnapshots.remove(blockSnapshot);
				return false;
			} else {
				BlockState currentLevelBlockstate = level.getBlockState(pos);
				if ((flag & 128) == 0 && currentLevelBlockstate != previoiusChunkBlockState && (currentLevelBlockstate.getLightBlock(level, pos) != oldOpacity || currentLevelBlockstate.getLightEmission(level, pos) != oldLight || currentLevelBlockstate.useShapeForLightOcclusion() || previoiusChunkBlockState.useShapeForLightOcclusion())) {
					level.getProfiler().push("queueCheckLight");
					level.getChunkSource().getLightEngine().checkBlock(pos);
					level.getProfiler().pop();
				}

				if (blockSnapshot == null) { // Don't notify clients or update physics while capturing blockstates
					level.markAndNotifyBlock(pos, levelChunk, previoiusChunkBlockState, state, flag, value512);
				}

				return true;
			}
		}
	}

	/**
	 * 
	 * @param levelChunk
	 * @param pos
	 * @param state
	 * @param updateFlag
	 * @return
	 */
	   public static BlockState setBlockStateForced(LevelChunk levelChunk, BlockPos pos, BlockState state, boolean updateFlag) {
		   int yPos = pos.getY();
	      LevelChunkSection levelChunkSection = levelChunk.getSection(levelChunk.getSectionIndex(yPos));
	      boolean flag = levelChunkSection.hasOnlyAir();
	      if (flag && (state.isAir() && !(state.getBlock() instanceof ISummonFlameBlock))) {
	         return null;
	      } else {
	         int x = pos.getX() & 15;
	         int y = yPos & 15;
	         int z = pos.getZ() & 15;
	         BlockState previousBlockState = setSectionBlockState(levelChunkSection, x, y, z, state, true);
	         if (previousBlockState == state) {
	            return null;
	         } else {
	            Block block = state.getBlock();
	            updateHeightmaps(levelChunk.getHeightmaps(), Heightmap.Types.MOTION_BLOCKING, x, y, z, state);
	            updateHeightmaps(levelChunk.getHeightmaps(), Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, y, z, state);
	            updateHeightmaps(levelChunk.getHeightmaps(), Heightmap.Types.OCEAN_FLOOR, x, y, z, state);
	            updateHeightmaps(levelChunk.getHeightmaps(), Heightmap.Types.WORLD_SURFACE, x, y, z, state);
		           
//	            levelChunk.heightmaps.get(Heightmap.Types.MOTION_BLOCKING).update(j, i, l, state);
//	            levelChunk.heightmaps.get(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES).update(j, i, l, state);
//	            levelChunk.heightmaps.get(Heightmap.Types.OCEAN_FLOOR).update(j, i, l, state);
//	            levelChunk.heightmaps.get(Heightmap.Types.WORLD_SURFACE).update(j, i, l, state);
	            boolean flag1 = levelChunkSection.hasOnlyAir();
	            if (flag != flag1) {
	               levelChunk.getLevel().getChunkSource().getLightEngine().updateSectionStatus(pos, flag1);
	            }

	            boolean flag2 = previousBlockState.hasBlockEntity();
	            if (!levelChunk.getLevel().isClientSide) {
	               previousBlockState.onRemove(levelChunk.getLevel(), pos, state, updateFlag);
	         } else if ((!previousBlockState.is(block) || !state.hasBlockEntity()) && flag2) {
	               levelChunk.removeBlockEntity(pos);
	            }

	            if (!levelChunkSection.getBlockState(x, y, z).is(block)) {
	               return null;
	            } else {
	            if (!levelChunk.getLevel().isClientSide && !levelChunk.getLevel().captureBlockSnapshots) {
	                  state.onPlace(levelChunk.getLevel(), pos, previousBlockState, updateFlag);
	               }

//	               if (state.hasBlockEntity()) {
//	                  BlockEntity blockentity = levelChunk.getBlockEntity(pos, LevelChunk.EntityCreationType.CHECK);
//	                  if (blockentity == null) {
//	                     blockentity = ((EntityBlock)block).newBlockEntity(pos, state);
//	                     if (blockentity != null) {
//	                        levelChunk.addAndRegisterBlockEntity(blockentity);
//	                     }
//	                  } else {
//	                     blockentity.setBlockState(state);
//	                     levelChunk.updateBlockEntityTicker(blockentity);
//	                  }
//	               }

	               levelChunk.setUnsaved(true);
	               return previousBlockState;
	            }
	         }
	      }
	}

	   public static BlockState setSectionBlockState(LevelChunkSection section, int x, int y, int z, BlockState newBlockState, boolean flag) {
		      BlockState previousBlockState;
		      if (flag) {
		         previousBlockState = section.getStates().getAndSet(x, y, z, newBlockState);
		      } else {
		         previousBlockState = section.getStates().getAndSetUnchecked(x, y, z, newBlockState);
		      }

		      FluidState previousFluidState = previousBlockState.getFluidState();
		      FluidState newFluidState = newBlockState.getFluidState();
		      
		      if (!previousBlockState.isAir() || previousBlockState.getBlock() instanceof ISummonFlameBlock) {
		         --section.nonEmptyBlockCount;
		         if (previousBlockState.isRandomlyTicking()) {
		            --section.tickingBlockCount;
		         }
		      }

		      if (!previousFluidState.isEmpty()) {
		         --section.tickingFluidCount;
		      }

//		      if (!newBlockState.isAir()) {
		      if (!newBlockState.isAir() || newBlockState.getBlock() instanceof ISummonFlameBlock) {
		         ++section.nonEmptyBlockCount;
		         if (newBlockState.isRandomlyTicking()) {
		            ++section.tickingBlockCount;
		         }
		      }

		      if (!newFluidState.isEmpty()) {
		         ++section.tickingFluidCount;
		      }

		      return previousBlockState;
		   }
	/**
	 * 
	 * @param heightmaps
	 * @param heightType
	 * @param x
	 * @param y
	 * @param z
	 * @param state
	 */
	private static void updateHeightmaps(Collection<Entry<Types, Heightmap>> heightmaps, Types heightType, int x, int y,
			int z, BlockState state) {
		for(Entry<Heightmap.Types, Heightmap> entry : heightmaps) {
			if (entry.getKey() == heightType) {
				entry.getValue().update(x, y, z, state);
			}
		}		
	}
}
