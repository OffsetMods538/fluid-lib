package testing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testing.init.ModFluidTags;
import top.offsetmonkey538.fluidlib.api.IFluidBehaviour;

public class TestingFluidBehaviour implements IFluidBehaviour {

    @Override
    public TagKey<Fluid> getTagKey() {
        return ModFluidTags.TESTING_FLUID;
    }

    @Override
    public double getEntityPushSpeed(Entity entity) {
        if (entity instanceof CreeperEntity) return 0.25;
        return 0.014;
    }

    @Override
    public void onEnter(Entity entity) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;

        if (entity instanceof CreeperEntity creeper) creeper.ignite();
    }

    @Override
    public void collisionTick(Entity entity) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;

        // Kill all slimes larger than the smallest size
        if (entity instanceof SlimeEntity slime) {
            if (slime.getSize() > 1) slime.kill();
        }
    }

    @Override
    public void onExit(Entity entity) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;

        // Create an explosion then a player exits the fluid
        if (entity instanceof PlayerEntity) {
            BlockPos pos = entity.getBlockPos();
            world.createExplosion(entity, pos.getX(), pos.getY(), pos.getZ(), 2, World.ExplosionSourceType.NONE);
        }
    }
}
