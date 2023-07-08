package top.offsetmonkey538.fluidlib.mixin.entity.ai.goal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.access.EntityAccess;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

@Mixin(targets = "net/minecraft/entity/mob/SlimeEntity$SwimmingGoal")
public abstract class SlimeEntitySwimmingGoal {
    @Shadow @Final private SlimeEntity slime;

    @ModifyExpressionValue(
            method = "canStart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/SlimeEntity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$allowRisingInCustomFluid(boolean original) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!((EntityAccess) slime).hasCollidedWith(behaviour)) return;
            if (!behaviour.canSwim(slime)) return;
            returnValue[0] = true;
        });

        return returnValue[0];
    }
}
