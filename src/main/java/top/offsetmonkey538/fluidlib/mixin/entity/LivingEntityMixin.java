package top.offsetmonkey538.fluidlib.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

/**
 * Makes entities drown in fluids with the {@link FluidLibTags#ALLOWS_DROWNING fluid-lib:allows_drowning} tag.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @ModifyExpressionValue(
            method = "baseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$allowDrowningInFluidInCorrectTag(boolean original) {
        return original || this.isSubmergedIn(FluidLibTags.ALLOWS_DROWNING);
    }

    @ModifyExpressionValue(
            method = {"travel", "tickMovement"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$allowSwimmingInCustomFluid0(boolean original) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!hasCollidedWith(behaviour)) return;
            if (!behaviour.canSwim((Entity) (Object) this)) return;
            returnValue[0] = true;
        });

        return returnValue[0];
    }

   @ModifyExpressionValue(
           method = "tickMovement",
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/entity/LivingEntity;getFluidHeight(Lnet/minecraft/registry/tag/TagKey;)D"
           )
   )
   private double fluidlib$allowSwimmingInCustomFluid1(double original) {
       final double[] returnValue = {original};

       ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach(((fluid, fluidBehaviour) -> {
           if (getFluidHeight(fluidBehaviour.getTagKey()) <= 0) return;
           returnValue[0] = getFluidHeight(fluidBehaviour.getTagKey());
       }));

        return returnValue[0];
   }

   @ModifyArg(
           method = "tickMovement",
           at = @At(
                   value = "INVOKE",
                   target = "Lnet/minecraft/entity/LivingEntity;swimUpward(Lnet/minecraft/registry/tag/TagKey;)V",
                   ordinal = 0
           )
   )
   @SuppressWarnings("unchecked")
   private TagKey<Fluid> fluidlib$allowSwimmingInCustomFluid2(TagKey<Fluid> original) {
       final TagKey<Fluid>[] returnValue = new TagKey[]{original};

       ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach(((fluid, fluidBehaviour) -> {
           if (!hasCollidedWith(fluidBehaviour)) return;
           returnValue[0] = fluidBehaviour.getTagKey();
       }));

       return returnValue[0];
   }
}
