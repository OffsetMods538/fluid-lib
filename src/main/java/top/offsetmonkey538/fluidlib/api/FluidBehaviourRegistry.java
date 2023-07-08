package top.offsetmonkey538.fluidlib.api;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

/**
 * Registry for {@link IFluidBehaviour}s.
 */
public interface FluidBehaviourRegistry {
    FluidBehaviourRegistry INSTANCE = new FluidBehaviourRegistryImpl();

    /**
     * Register a fluid behaviour for the provided fluid.
     * <br />
     * You probably want to use the {@link #register(Fluid, Fluid, IFluidBehaviour)} method if your fluid is a {@link FlowableFluid}.
     *
     * @param fluidBehaviour The fluid behaviour to register.
     * @param fluid The fluid to register the behaviour for.
     * @see #register(Fluid, Fluid, IFluidBehaviour)
     */
    void register(Fluid fluid, IFluidBehaviour fluidBehaviour);

    /**
     * Register a fluid behaviour for the provided still and flowing version of the fluid.
     *
     * @param fluidBehaviour The fluid behaviour to register.
     * @param still The still version of the fluid.
     * @param flowing The flowing version of the fluid.
     * @see #register(Fluid, IFluidBehaviour)
     */
    default void register(Fluid still, Fluid flowing, IFluidBehaviour fluidBehaviour) {
        register(still, fluidBehaviour);
        register(flowing, fluidBehaviour);
    }

    /**
     * Get the {@link IFluidBehaviour} for a specific fluid.
     * @param fluid The fluid.
     * @return The {@link IFluidBehaviour} for the provided fluid.
     * @see #get(FluidState)
     */
    IFluidBehaviour get(Fluid fluid);

    /**
     * Get the {@link IFluidBehaviour} for a specific fluid state.
     * @param fluid The fluid.
     * @return The {@link IFluidBehaviour} for the provided fluid state.
     * @see #get(Fluid)
     */
    default IFluidBehaviour get(FluidState fluid) {
        return get(fluid.getFluid());
    }
}
