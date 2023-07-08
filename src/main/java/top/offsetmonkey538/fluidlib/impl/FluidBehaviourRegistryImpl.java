package top.offsetmonkey538.fluidlib.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.fluid.Fluid;
import top.offsetmonkey538.fluidlib.api.IFluidBehaviour;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;

import static top.offsetmonkey538.fluidlib.FluidLib.*;

public class FluidBehaviourRegistryImpl implements FluidBehaviourRegistry {
    private final Map<Fluid, IFluidBehaviour> fluidBehaviours = new HashMap<>();

    @Override
    public void register(Fluid fluid, IFluidBehaviour fluidBehaviour) {
        if (fluidBehaviours.containsKey(fluid)) {
            LOGGER.warn("Tried registering fluid behaviour for fluid '{}', but it already has fluid behaviour. Ignoring!", fluid);
            return;
        }
        fluidBehaviours.put(fluid, fluidBehaviour);
    }

    @Override
    public IFluidBehaviour get(Fluid fluid) {
        return fluidBehaviours.get(fluid);
    }

    public void forEach(BiConsumer<Fluid, IFluidBehaviour> action) {
        fluidBehaviours.forEach(action);
    }
}
