package com.edgarluque.fabric.fans.entities;

import com.edgarluque.fabric.fans.Fans;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class FanBlockEntity extends BlockEntity {
    private double pushSpeed;
    private int reach;

    public FanBlockEntity(BlockPos pos, BlockState state) {
        super(Fans.FAN_BLOCK_ENTITY, pos, state);
        pushSpeed = 0.2;
        reach = 10;
    }

    public double getPushSpeed() {
        return pushSpeed;
    }

    public void setPushSpeed(double pushSpeed) {
        this.pushSpeed = pushSpeed;
        markDirty();
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
        markDirty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, FanBlockEntity blockEntity) {
        if(world.isClient()) return;

        Direction dir = state.get(Properties.HORIZONTAL_FACING);
        int expandZ = 0;
        int expandX = 0;
        int reach = blockEntity.getReach();
        double pushSpeed = blockEntity.getPushSpeed();

        switch (dir) {
            case SOUTH -> expandZ += reach;
            case NORTH -> expandZ -= reach;
            case EAST -> expandX += reach;
            case WEST -> expandX -= reach;
        }

        int sign = expandX < 0 ? -1 : 1;
        for(int i = 1; i <= Math.abs(expandX); i++) {
            BlockState block = world.getBlockState(pos.add(i * sign, 0, 0));

            if(!block.getBlock().canMobSpawnInside()) {
                if(sign < 0) {
                    expandX = -i + 1;
                } else {
                    expandX = i - 1;
                }
            }
        }

        sign = expandZ < 0 ? -1 : 1;
        for(int i = 1; i <= Math.abs(expandZ); i++) {
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

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        reach = nbt.getInt("reach");
        pushSpeed = nbt.getDouble("pushSpeed");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("reach", reach);
        nbt.putDouble("pushSpeed", pushSpeed);
        super.writeNbt(nbt);
    }
}
