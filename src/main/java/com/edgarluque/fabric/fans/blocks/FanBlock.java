package com.edgarluque.fabric.fans.blocks;

import com.edgarluque.fabric.fans.Fans;
import com.edgarluque.fabric.fans.entities.FanBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FanBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    private double pushSpeed;
    private int reach;

    public FanBlock(Settings settings, double pushSpeed, int reach) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        this.pushSpeed = pushSpeed;
        this.reach = reach;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        FanBlockEntity entity = new FanBlockEntity(pos, state);
        entity.setPushSpeed(pushSpeed);
        entity.setReach(reach);
        return entity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Fans.FAN_BLOCK_ENTITY, FanBlockEntity::tick);
    }


    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
