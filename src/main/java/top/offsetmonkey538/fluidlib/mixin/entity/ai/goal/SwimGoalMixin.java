package top.offsetmonkey538.fluidlib.mixin.entity.ai.goal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.access.EntityAccess;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

@Mixin(SwimGoal.class)
public abstract class SwimGoalMixin {

    @Shadow @Final private MobEntity mob;

    @ModifyExpressionValue(
            method = "canStart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/MobEntity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$allowRisingInCustomFluid0(boolean original) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!((EntityAccess) mob).hasCollidedWith(behaviour)) return;
            if (!behaviour.canSwim(mob)) return;
            returnValue[0] = true;
        });

        return returnValue[0];
    }
    @ModifyExpressionValue(
            method = "canStart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/MobEntity;getFluidHeight(Lnet/minecraft/registry/tag/TagKey;)D"
            )
    )
    private double fluidlib$allowRisingInCustomFluid1(double original) {
        final double[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach(((fluid, behaviour) -> {
            if (mob.getFluidHeight(behaviour.getTagKey()) <= 0) return;
            returnValue[0] = mob.getFluidHeight(behaviour.getTagKey());
        }));

        return returnValue[0];
    }

}
