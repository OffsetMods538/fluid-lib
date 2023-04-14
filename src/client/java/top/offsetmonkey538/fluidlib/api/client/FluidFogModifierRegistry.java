package top.offsetmonkey538.fluidlib.api.client;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import top.offsetmonkey538.fluidlib.api.client.fog.IFluidFogModifier;
import top.offsetmonkey538.fluidlib.impl.client.FluidFogModifierRegistryImpl;

/**
 * Registry for {@link IFluidFogModifier IFluidFogModifier}s.
 */
public interface FluidFogModifierRegistry {
    FluidFogModifierRegistry INSTANCE = new FluidFogModifierRegistryImpl();

    /**
     * Register a fog modifier for a fluid.
     * @param fluid The fluid tag
     * @param fogModifier The fog modifier
     */
    void register(TagKey<Fluid> fluid, IFluidFogModifier fogModifier);

    /**
     * Get the {@link IFluidFogModifier IFluidFogModifier} for a specific fluid tag.
     * @param fluid The fluid tag
     * @return The {@link IFluidFogModifier IFluidFogModifier} for the fluid tag.
     */
    IFluidFogModifier get(TagKey<Fluid> fluid);
}
