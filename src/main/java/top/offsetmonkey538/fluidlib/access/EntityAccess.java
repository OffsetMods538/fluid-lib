package top.offsetmonkey538.fluidlib.access;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.api.IFluidBehaviour;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

/**
 * Use for checking if an entity has collided with a fluid.
 */
public interface EntityAccess {
    boolean hasCollidedWith(IFluidBehaviour behaviour);
    default boolean hasCollidedWith(Fluid fluid) {
        final boolean[] returnValue = {false};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid2, behaviour) -> {
            if (fluid != fluid2) return;
            if (!hasCollidedWith(behaviour)) return;
            returnValue[0] = true;
        });

        return returnValue[0];
    }
    default boolean hasCollidedWith(FluidState fluidState) {
        final boolean[] returnValue = {false};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid2, behaviour) -> {
            if (fluidState.isIn(behaviour.getTagKey())) return;
            if (!hasCollidedWith(behaviour)) return;
            returnValue[0] = true;
        });

        return returnValue[0];
    }
}
