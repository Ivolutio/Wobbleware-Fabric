package net.wobblesoft.wobbleware.wobbleware.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.wobblesoft.wobbleware.wobbleware.Wobbleware;

import java.util.ArrayList;
import java.util.Random;

public class CrystalElevatorEntity extends BlockEntity {
    private static int TELEPORT_DELAY = 20;

    private ArrayList<Entity> currentEntities;
    private ArrayList<Integer> currentEntitiesTimer;

    private Random random;

    public CrystalElevatorEntity(BlockPos pos, BlockState state) {
        super(Wobbleware.CRYSTAL_ELEVATOR_ENTITY, pos, state);

        currentEntities = new ArrayList<>();
        currentEntitiesTimer = new ArrayList<Integer>();

        random = new Random();
    }


    public static void tick(World world, BlockPos pos, BlockState state, CrystalElevatorEntity be) {
        if (!world.isClient) {

            Vec3d myPos = new Vec3d(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);

            for (int i = 0; i < be.currentEntities.size(); i++) {
                Entity entity = be.currentEntities.get(i);
                var distFromBlock = entity.getPos().squaredDistanceTo(myPos);
//            System.out.println(" dist: " + distFromBlock);
                if (distFromBlock > 0.25) { // when entity is off the block, remove it from the list and stop ticking it
                    be.currentEntities.remove(i);
                    be.currentEntitiesTimer.remove(i);
                } else {
                    var timer = be.currentEntitiesTimer.get(i);
                    timer++;
                    be.currentEntitiesTimer.set(i, timer);

                    if (timer % 4 == 0)
                        world.playSound(null, pos.up(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.BLOCKS, 1f-timer/TELEPORT_DELAY, timer);


                    if (timer > TELEPORT_DELAY) {
                        teleportEntity(world, pos, entity);

                        if (be.currentEntities.size() > i)
                            be.currentEntities.remove(i);
                        if (be.currentEntitiesTimer.size() > i)
                            be.currentEntitiesTimer.remove(i);
                    }
                }
            }
        }

//        if(world.isClient){
//
//            for (int i = 0; i < be.currentEntities.size(); i++) {
//            world.addParticle(ParticleTypes.PORTAL, pos.getX()+.5f, pos.getY()+2f, pos.getZ()+.5f, (be.random.nextDouble() - 0.5D) * 2.0D, -be.random.nextDouble(), (be.random.nextDouble() - 0.5D) * 2.0D);
//        }}

    }

    public void addEntity(Entity entity) {
        if (currentEntities.contains(entity)) return;
        currentEntities.add(entity);
        currentEntitiesTimer.add(0);
    }

    private static void teleportEntity(World world, BlockPos pos, Entity entity) {
        BlockPos targetPos = null;
        for (int y = 1; y <= 16; y++) {
            var testPos = new BlockPos(pos.getX(), pos.getY() + y, pos.getZ());
            var block = world.getBlockState(testPos);
            if (!block.isAir()) {
                if (world.getBlockState(new BlockPos(testPos.getX(), testPos.getY() + 1, testPos.getZ())).isAir() && world.getBlockState(new BlockPos(testPos.getX(), testPos.getY() + 2, testPos.getZ())).isAir()) {
                    targetPos = testPos;
                    break;
                }
            }
        }

        if (targetPos != null) {
            world.playSound(null, targetPos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 1f, .1f);
            entity.teleport(targetPos.getX() + 0.5, targetPos.getY() + 1, targetPos.getZ() + 0.5);
        } else {
            world.playSound(null, pos.up(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.BLOCKS, 1f, 1f);
        }
    }
}
