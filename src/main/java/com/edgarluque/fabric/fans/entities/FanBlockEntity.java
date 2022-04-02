package com.edgarluque.fabric.fans.entities;

import com.edgarluque.fabric.fans.Fans;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class FanBlockEntity extends BlockEntity {
    public FanBlockEntity(BlockPos pos, BlockState state) {
        super(Fans.FAN_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {
        if(world.isClient()) return;

        Direction dir = state.get(Properties.HORIZONTAL_FACING);
        int expandZ = 0;
        int expandX = 0;

        int reach = 10;
        double pushSpeed = 0.2;

        switch (dir) {
            case SOUTH -> expandZ += reach;
            case NORTH -> expandZ -= reach;
            case EAST -> expandX += reach;
            case WEST -> expandX -= reach;
        }

        for(int i = 1; i <= Math.abs(expandX); i++) {
            int sign = expandX < 0 ? -1 : 1;
            BlockState block = world.getBlockState(pos.add(i * sign, 0, 0));

            if(!block.getBlock().canMobSpawnInside()) {
                if(sign < 0) {
                    expandX = -i + 1;
                } else {
                    expandX = i - 1;
                }
            }
        }

        for(int i = 1; i <= Math.abs(expandZ); i++) {
            int sign = expandZ < 0 ? -1 : 1;
            BlockState block = world.getBlockState(pos.add(0, 0, i * sign));

            if(!block.getBlock().canMobSpawnInside()) {
                if(sign < 0) {
                    expandZ = -i + 1;
                } else {
                    expandZ = i - 1;
                }
            }
        }

        Box box = new Box(pos.getX(), pos.getY(), pos.getZ(),
                pos.getX() + 1 + expandX, pos.getY() + 1, pos.getZ() + 1 + expandZ);

        List<LivingEntity> mobs = world.getNonSpectatingEntities(LivingEntity.class, box);

        for(LivingEntity mob: mobs) {
            switch (dir) {
                case SOUTH -> mob.addVelocity(0, 0, pushSpeed);
                case NORTH -> mob.addVelocity(0, 0, -pushSpeed);
                case EAST -> mob.addVelocity(pushSpeed, 0, 0);
                case WEST -> mob.addVelocity(-pushSpeed, 0, 0);
            }
        }
    }
}
