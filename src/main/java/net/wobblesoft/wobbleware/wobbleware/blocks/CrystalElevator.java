package net.wobblesoft.wobbleware.wobbleware.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.wobblesoft.wobbleware.wobbleware.Wobbleware;

public class CrystalElevator extends BlockWithEntity {

    public CrystalElevator(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalElevatorEntity(pos, state);
    }
//
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, Wobbleware.CRYSTAL_ELEVATOR_ENTITY, (world1, pos, state1, be) -> CrystalElevatorEntity.tick(world1, pos, state1, be));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);

        if(entity instanceof ItemEntity) return;

//        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystalElevatorEntity) {
                CrystalElevatorEntity crystalElevatorEntity = (CrystalElevatorEntity)blockEntity;
                crystalElevatorEntity.addEntity(entity);
            }
//        }


    }
}
