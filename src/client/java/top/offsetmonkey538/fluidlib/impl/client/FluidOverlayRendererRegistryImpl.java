package top.offsetmonkey538.fluidlib.impl.client;

import net.minecraft.fluid.Fluid;
import top.offsetmonkey538.fluidlib.api.client.renderer.FluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.api.client.FluidOverlayRendererRegistry;

import java.util.HashMap;
import java.util.Map;

public class FluidOverlayRendererRegistryImpl implements FluidOverlayRendererRegistry {
    private final Map<Fluid, FluidOverlayRenderer> renderers = new HashMap<>();

    @Override
    public void register(Fluid fluid, FluidOverlayRenderer renderer) {
        renderers.put(fluid, renderer);
    }

    @Override
    public FluidOverlayRenderer get(Fluid fluid) {
        return renderers.get(fluid);
    }
}
