package com.edgarluque.fabric.fans.entities;

import com.edgarluque.fabric.fans.Fans;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FanBlockEntity extends BlockEntity {
    public FanBlockEntity(BlockPos pos, BlockState state) {
        super(Fans.FAN_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FanBlockEntity be) {
        Fans.LOGGER.info("ticking");
    }
}
