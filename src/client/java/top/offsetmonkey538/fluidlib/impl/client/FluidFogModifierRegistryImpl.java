package top.offsetmonkey538.fluidlib.impl.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import top.offsetmonkey538.fluidlib.api.client.FluidFogModifierRegistry;
import top.offsetmonkey538.fluidlib.api.client.fog.IFluidFogModifier;

import static top.offsetmonkey538.fluidlib.FluidLib.*;

public class FluidFogModifierRegistryImpl implements FluidFogModifierRegistry {
    private final Map<TagKey<Fluid>, IFluidFogModifier> fogModifiers = new HashMap<>();

    @Override
    public void register(TagKey<Fluid> fluid, IFluidFogModifier fogModifier) {
        if (fogModifiers.containsKey(fluid)) {
            LOGGER.warn("Tried registering fog modifier '{}' for fluid '{}', but it already has fog modifier '{}'. Ignoring!", fogModifier, fluid.id(), get(fluid));
            return;
        }
        fogModifiers.put(fluid, fogModifier);
    }

    @Override
    public IFluidFogModifier get(TagKey<Fluid> fluid) {
        return fogModifiers.get(fluid);
    }

    public Stream<TagKey<Fluid>> matches(Predicate<TagKey<Fluid>> check) {
        return fogModifiers.keySet().stream().filter(check);
    }
}
