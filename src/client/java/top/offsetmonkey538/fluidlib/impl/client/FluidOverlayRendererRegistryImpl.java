package top.offsetmonkey538.fluidlib.impl.client;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import top.offsetmonkey538.fluidlib.api.client.renderer.overlay.FluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.api.client.FluidOverlayRendererRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static top.offsetmonkey538.fluidlib.FluidLib.LOGGER;

public class FluidOverlayRendererRegistryImpl implements FluidOverlayRendererRegistry {
    private final Map<TagKey<Fluid>, FluidOverlayRenderer> renderers = new HashMap<>();

    @Override
    public void register(TagKey<Fluid> fluid, FluidOverlayRenderer renderer) {
        if (renderers.containsKey(fluid)) {
            LOGGER.warn("Tried registering overlay renderer '{}' for fluid '{}', but it already has overlay renderer '{}'. Ignoring!", renderer, fluid.id(), get(fluid));
            return;
        }
        renderers.put(fluid, renderer);
    }

    @Override
    public FluidOverlayRenderer get(TagKey<Fluid> fluid) {
        return renderers.get(fluid);
    }

    public Stream<TagKey<Fluid>> matches(Predicate<TagKey<Fluid>> check) {
        return renderers.keySet().stream().filter(check);
    }
}
