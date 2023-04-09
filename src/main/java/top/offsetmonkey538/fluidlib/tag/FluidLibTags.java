package top.offsetmonkey538.fluidlib.tag;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static top.offsetmonkey538.fluidlib.FluidLib.id;

public final class FluidLibTags {
    private FluidLibTags() {

    }

    /**
     * Add your fluid to this tag to make concrete powder turn into concrete when touching your fluid.
     */
    public static final TagKey<Fluid> HARDENS_CONCRETE = TagKey.of(RegistryKeys.FLUID, id("hardens_concrete"));

    /**
     * Add your fluid to this tag to make coral not die while touching your fluid.
     */
    public static final TagKey<Fluid> KEEPS_CORAL_ALIVE = TagKey.of(RegistryKeys.FLUID, id("keeps_coral_alive"));

    /**
     * Add your fluid to this tag to farmland not dry while touching your fluid.
     */
    public static final TagKey<Fluid> WATERS_FARMLAND = TagKey.of(RegistryKeys.FLUID, id("waters_farmland"));
}
