package top.offsetmonkey538.fluidlib;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;

/**
 * Implement this in your {@link Fluid} class.
 */
public interface IFluid {

    /**
     * @return The {@link TagKey} for the fluid.
     */
    TagKey<Fluid> getTagKey();
}
