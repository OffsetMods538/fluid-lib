package top.offsetmonkey538.fluidlib.api;

import net.minecraft.entity.Entity;

/**
 * Implement this in your <code>Fluid</code> class
 */
public interface IFluidLibFluid {

    /**
     * Override this to run custom code when an entity is in the liquid
     * @param entity The entity that's in the liquid
     */
    default void onEntityCollide(Entity entity) {

    }
}
