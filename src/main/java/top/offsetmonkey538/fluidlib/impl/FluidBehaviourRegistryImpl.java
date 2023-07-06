package top.offsetmonkey538.fluidlib.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import top.offsetmonkey538.fluidlib.api.FluidBehaviour;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;

import static top.offsetmonkey538.fluidlib.FluidLib.*;

public class FluidBehaviourRegistryImpl implements FluidBehaviourRegistry {
    private final Map<Fluid, FluidBehaviour> fluidBehaviours = new HashMap<>();

    @Override
    public void register(Fluid fluid, FluidBehaviour fluidBehaviour) {
        if (fluidBehaviours.containsKey(fluid)) {
            LOGGER.warn("Tried registering fluid behaviour for fluid '{}', but it already has fluid behaviour. Ignoring!", fluid);
            return;
        }
        fluidBehaviours.put(fluid, fluidBehaviour);
    }

    @Override
    public FluidBehaviour get(Fluid fluid) {
        return fluidBehaviours.get(fluid);
    }

    public void forEach(BiConsumer<Fluid, FluidBehaviour> action) {
        fluidBehaviours.forEach(action);
    }

    public boolean contains(Fluid fluid) {
        return fluidBehaviours.containsKey(fluid);
    }

    public boolean contains(FluidState fluidState) {
        return contains(fluidState.getFluid());
    }
}
