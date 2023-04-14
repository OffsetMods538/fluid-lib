package top.offsetmonkey538.fluidlib.api.client;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import top.offsetmonkey538.fluidlib.api.client.renderer.overlay.FluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.impl.client.FluidOverlayRendererRegistryImpl;

/**
 * Registry for {@link FluidOverlayRenderer FluidOverlayRenderer}s.
 */
public interface FluidOverlayRendererRegistry {
    FluidOverlayRendererRegistry INSTANCE = new FluidOverlayRendererRegistryImpl();

    /**
     * Register an overlay renderer for a fluid.
     * @param fluid The fluid tag
     * @param renderer The overlay renderer
     */
    void register(TagKey<Fluid> fluid, FluidOverlayRenderer renderer);

    /**
     * Get the {@link FluidOverlayRenderer FluidOverlayRenderer} for a specific fluid tag.
     * @param fluid The fluid tag
     * @return The {@link FluidOverlayRenderer FluidOverlayRenderer} for the fluid tag.
     */
    FluidOverlayRenderer get(TagKey<Fluid> fluid);
}
