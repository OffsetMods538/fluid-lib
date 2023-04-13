package top.offsetmonkey538.fluidlib.api.client;

import net.minecraft.fluid.Fluid;
import top.offsetmonkey538.fluidlib.api.client.renderer.overlay.FluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.impl.client.FluidOverlayRendererRegistryImpl;

/**
 * Registry for {@link FluidOverlayRenderer FluidOverlayRenderer}s.
 */
public interface FluidOverlayRendererRegistry {
    FluidOverlayRendererRegistry INSTANCE = new FluidOverlayRendererRegistryImpl();

    /**
     * Register an overlay renderer for a fluid.
     * <br>
     * The fluid has to implement {@link top.offsetmonkey538.fluidlib.IFluid IFluid}.
     * @param fluid The fluid
     * @param renderer The overlay renderer
     */
    void register(Fluid fluid, FluidOverlayRenderer renderer);

    /**
     * Register an overlay renderer for multiple fluids.
     * <br>
     * The fluids have to implement {@link top.offsetmonkey538.fluidlib.IFluid IFluid}.
     * @param still The still variant of the fluid.
     * @param flowing The flowing variant of the fluid.
     * @param renderer The overlay renderer.
     */
    default void register(Fluid still, Fluid flowing, FluidOverlayRenderer renderer) {
        register(still, renderer);
        register(flowing, renderer);
    }

    /**
     * Get the {@link FluidOverlayRenderer FluidOverlayRenderer} for a specific fluid.
     * @param fluid The fluid
     * @return The {@link FluidOverlayRenderer FluidOverlayRenderer} for the fluid.
     */
    FluidOverlayRenderer get(Fluid fluid);
}
