package top.offsetmonkey538.fluidlib.api;

import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;

/**
 * Custom behaviour for a fluid. Register using the {@link FluidBehaviourRegistry}.
 */
public interface FluidBehaviour {

    /**
     * @return The {@link TagKey} for the fluid.
     */
    TagKey<Fluid> getTagKey();

    /**
     * Get the pushing speed for the provided entity.
     * <br />
     * Default implementation returns <code>0.014</code> which is the vanilla pushing speed for water.
     *
     * @param entity The entity to get the push speed for.
     * @return The pushing speed for the provided entity.
     */
    default double getEntityPushSpeed(Entity entity) {
        return 0.014;
    }

    /**
     * Run when the provided entity enters the fluid.
     *
     * @param entity The entity.
     */
    default void onEnter(Entity entity) {

    }

    /**
     * Run every tick while the provided entity is touching the fluid.
     *
     * @param entity The entity.
     */
    default void collisionTick(Entity entity) {

    }

    /**
     * Run when the provided entity leaves the fluid.
     *
     * @param entity The entity.
     */
    default void onExit(Entity entity) {

    }
}
