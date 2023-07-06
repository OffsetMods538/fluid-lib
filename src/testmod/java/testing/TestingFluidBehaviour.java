package testing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import testing.init.ModFluidTags;
import top.offsetmonkey538.fluidlib.api.FluidBehaviour;

public class TestingFluidBehaviour implements FluidBehaviour {

    @Override
    public TagKey<Fluid> getTagKey() {
        return ModFluidTags.TESTING_FLUID;
    }

    @Override
    public double getEntityPushSpeed(Entity entity) {
        if (entity instanceof CreeperEntity) return 0.25;
        return 0.014;
    }
}
