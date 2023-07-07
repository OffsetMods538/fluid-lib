package top.offsetmonkey538.fluidlib.api;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

/**
 * Registry for {@link FluidBehaviour}s.
 */
public interface FluidBehaviourRegistry {
    FluidBehaviourRegistry INSTANCE = new FluidBehaviourRegistryImpl();

    /**
     * Register a fluid behaviour for the provided fluid.
     * <br />
     * You probably want to use the {@link #register(Fluid, Fluid, FluidBehaviour)} method if your fluid is a {@link FlowableFluid}.
     *
     * @param fluidBehaviour The fluid behaviour to register.
     * @param fluid The fluid to register the behaviour for.
     * @see #register(Fluid, Fluid, FluidBehaviour)
     */
    void register(Fluid fluid, FluidBehaviour fluidBehaviour);

    /**
     * Register a fluid behaviour for the provided still and flowing version of the fluid.
     *
     * @param fluidBehaviour The fluid behaviour to register.
     * @param still The still version of the fluid.
     * @param flowing The flowing version of the fluid.
     * @see #register(Fluid, FluidBehaviour)
     */
    default void register(Fluid still, Fluid flowing, FluidBehaviour fluidBehaviour) {
        register(still, fluidBehaviour);
        register(flowing, fluidBehaviour);
    }

    /**
     * Get the {@link FluidBehaviour} for a specific fluid.
     * @param fluid The fluid.
     * @return The {@link FluidBehaviour} for the provided fluid.
     * @see #get(FluidState)
     */
    FluidBehaviour get(Fluid fluid);

    /**
     * Get the {@link FluidBehaviour} for a specific fluid state.
     * @param fluid The fluid.
     * @return The {@link FluidBehaviour} for the provided fluid state.
     * @see #get(Fluid)
     */
    default FluidBehaviour get(FluidState fluid) {
        return get(fluid.getFluid());
    }

    /**
     * Check if the provided fluid is registered.
     *
     * @param fluid The fluid to check.
     * @return If the provided fluid is registered.
     */
    boolean contains(Fluid fluid);

    /**
     * Check if the provide fluid state is registered.
     *
     * @param fluidState The fluid state to check.
     * @return If the provided fluid is registered.
     */
    default boolean contains(FluidState fluidState) {
        return contains(fluidState.getFluid());
    }
}
