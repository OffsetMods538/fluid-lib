package top.offsetmonkey538.fluidlib.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.access.EntityAccess;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends EntityMixin {

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ItemEntity;getFluidHeight(Lnet/minecraft/registry/tag/TagKey;)D",
                    ordinal = 0
            )
    )
    private double fluidlib$applyWaterBuoyancyToCustomFluid0(double original) {
        final double[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach(((fluid, fluidBehaviour) -> {
            if (getFluidHeight(fluidBehaviour.getTagKey()) <= 0) return;
            returnValue[0] = getFluidHeight(fluidBehaviour.getTagKey());
        }));

        return returnValue[0];
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ItemEntity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$applyWaterBuoyancyToCustomFluid1(boolean original) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach(((fluid, fluidBehaviour) -> {
            if (!((EntityAccess) this).hasCollidedWith(fluidBehaviour)) return;
            returnValue[0] = true;
        }));

        return returnValue[0];
    }
}
