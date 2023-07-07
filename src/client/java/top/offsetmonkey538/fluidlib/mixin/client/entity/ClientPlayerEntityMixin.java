package top.offsetmonkey538.fluidlib.mixin.client.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;
import top.offsetmonkey538.fluidlib.mixin.entity.EntityMixin;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends EntityMixin {

    @ModifyExpressionValue(
            method = {"tickMovement", "isWalking"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSubmergedInWater()Z"
            )
    )
    private boolean fluidlib$allowSwimmingInCustomFluid0(boolean original) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!isSubmergedIn(behaviour.getTagKey())) return;
            if (!behaviour.canSwim((Entity) (Object) this)) return;

            returnValue[0] = true;
        });

        return returnValue[0];
    }

    @ModifyExpressionValue(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$allowSwimmingInCustomFluid1(boolean original) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!fluidlib$collidedFluids.contains(behaviour)) return;
            if (!behaviour.canSwim((Entity) (Object) this)) return;

            returnValue[0] = true;
        });

        return returnValue[0];
    }
}
