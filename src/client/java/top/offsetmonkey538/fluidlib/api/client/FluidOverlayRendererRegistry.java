package top.offsetmonkey538.fluidlib.api.client;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import top.offsetmonkey538.fluidlib.api.client.renderer.IFluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.impl.client.FluidOverlayRendererRegistryImpl;

/**
 * Registry for {@link IFluidOverlayRenderer IFluidOverlayRenderer}s.
 */
public interface FluidOverlayRendererRegistry {
    FluidOverlayRendererRegistry INSTANCE = new FluidOverlayRendererRegistryImpl();

    /**
     * Register an overlay renderer for a fluid.
     * @param fluid The fluid tag
     * @param renderer The overlay renderer
     */
    void register(TagKey<Fluid> fluid, IFluidOverlayRenderer renderer);

    /**
     * Get the {@link IFluidOverlayRenderer IFluidOverlayRenderer} for a specific fluid tag.
     * @param fluid The fluid tag
     * @return The {@link IFluidOverlayRenderer IFluidOverlayRenderer} for the fluid tag.
     */
    IFluidOverlayRenderer get(TagKey<Fluid> fluid);
}
