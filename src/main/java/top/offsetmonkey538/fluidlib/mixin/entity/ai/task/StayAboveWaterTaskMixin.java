package top.offsetmonkey538.fluidlib.mixin.entity.ai.task;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.access.EntityAccess;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

@Mixin(StayAboveWaterTask.class)
public abstract class StayAboveWaterTaskMixin {

    @ModifyExpressionValue(
            method = "shouldRun",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/MobEntity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$allowSwimmingInCustomFluid0(boolean original, ServerWorld serverWorld, MobEntity mobEntity) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!((EntityAccess) mobEntity).hasCollidedWith(behaviour)) return;
            if (!behaviour.canSwim(mobEntity)) return;
            returnValue[0] = true;
        });

        return returnValue[0];
    }
}
