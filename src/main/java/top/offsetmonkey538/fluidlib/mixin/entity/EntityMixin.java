package top.offsetmonkey538.fluidlib.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.fluidlib.api.FluidBehaviour;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.impl.FluidBehaviourRegistryImpl;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract World getWorld();

    @Shadow public float fallDistance; // TODO

    @Shadow public abstract boolean isSubmergedIn(TagKey<Fluid> fluidTag);

    @Shadow public abstract double getFluidHeight(TagKey<Fluid> fluid);

    @Unique
    public final List<FluidBehaviour> fluidlib$collidedFluids = new ArrayList<>();

    @Inject(
            method = "baseTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;updateSwimming()V",
                    shift = At.Shift.BEFORE
            )
    )
    private void fluidlib$fluidPhysics(CallbackInfo ci) {
        final Entity entity = (Entity) (Object) this;

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            double pushSpeed = behaviour.getEntityPushSpeed(entity);

            if (entity.updateMovementInFluid(behaviour.getTagKey(), pushSpeed)) {
                behaviour.collisionTick(entity);
                fallDistance = 0;

                if (fluidlib$collidedFluids.contains(behaviour)) return;
                fluidlib$collidedFluids.add(behaviour);
                behaviour.onEnter(entity);

                return;
            }

            if (!fluidlib$collidedFluids.contains(behaviour)) return;

            fluidlib$collidedFluids.remove(behaviour);
            behaviour.onExit(entity);
        });
    }

    @ModifyExpressionValue(
            method = "updateSwimming",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$continueSwimmingIfInCustomFluid(boolean original) {
        final boolean[] returnValue = {false};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!fluidlib$collidedFluids.contains(behaviour)) return;
            if (!behaviour.canSwim((Entity) (Object) this)) return;

            returnValue[0] = true;
        });

        return original || returnValue[0];
    }
    @ModifyExpressionValue(
            method = "updateSwimming",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isSubmergedInWater()Z"
            )
    )
    private boolean fluidlib$startSwimmingIfInCustomFluid1(boolean original) {
        final boolean[] returnValue = {false};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!isSubmergedIn(behaviour.getTagKey())) return;
            if (!behaviour.canSwim((Entity) (Object) this)) return;

            returnValue[0] = true;
        });

        return original || returnValue[0];
    }

    @WrapOperation(
            method = "updateSwimming",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$startSwimmingIfInCustomFluid2(FluidState instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        FluidBehaviour behaviour = FluidBehaviourRegistry.INSTANCE.get(instance);
        if (behaviour == null || !behaviour.canSwim((Entity) (Object) this) || !instance.isIn(behaviour.getTagKey())) return original.call(instance, tag);

        return true;
    }

    @ModifyExpressionValue(
            method = {"isCrawling", "shouldSpawnSprintingParticles"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isTouchingWater()Z"
            )
    )
    private boolean fluidlib$startSwimmingIfInCustomFluid3(boolean original) {
        final boolean[] returnValue = {original};

        ((FluidBehaviourRegistryImpl) FluidBehaviourRegistry.INSTANCE).forEach((fluid, behaviour) -> {
            if (!fluidlib$collidedFluids.contains(behaviour)) return;
            if (!behaviour.canSwim((Entity) (Object) this)) return;

            returnValue[0] = true;
        });

        return returnValue[0];
    }
}
