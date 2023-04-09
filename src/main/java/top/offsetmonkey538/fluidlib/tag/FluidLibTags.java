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
     * Add your fluid to this tag to make farmland not dry while touching your fluid.
     */
    public static final TagKey<Fluid> WATERS_FARMLAND = TagKey.of(RegistryKeys.FLUID, id("waters_farmland"));


    /**
     * Add your fluid to this tag to make respawn anchor explosions not break blocks when touching your fluid.
     */
    public static final TagKey<Fluid> DISABLES_RESPAWN_ANCHOR_EXPLOSION = TagKey.of(RegistryKeys.FLUID, id("disables_respawn_anchor_explosion"));
}
