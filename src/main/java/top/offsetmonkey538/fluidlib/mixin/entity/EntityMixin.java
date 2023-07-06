package top.offsetmonkey538.fluidlib.mixin.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
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

    @Unique
    private final List<FluidBehaviour> fluidlib$collidedFluids = new ArrayList<>();

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
//
//
//    //// SWIMMING ////
//
//    @ModifyExpressionValue(
//            method = "updateSwimming",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/Entity;isTouchingWater()Z"
//            )
//    )
//    private boolean fluidlib$continueSwimmingIfInCustomFluid(boolean original) {
//        if (fluidlib$getFluid() instanceof IFluid fluid && fluid.canSwim((Entity) (Object) this)) return true;
//        return original;
//    }
//    @ModifyExpressionValue(
//            method = "updateSwimming",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/Entity;isSubmergedInWater()Z"
//            )
//    )
//    private boolean fluidlib$makeCustomFluidSwimmable(boolean original) {
//        if (!(fluidlib$getFluid() instanceof IFluid fluid && fluid.canSwim((Entity) (Object) this))) return original;
//        return isSubmergedIn(fluid.getTagKey());
//    }
//
//    @ModifyArg(
//            method = "updateSwimming",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
//            )
//    )
//    private TagKey<Fluid> fluidlib$makeCustomFluidSwimmable(TagKey<Fluid> original) {
//        if (fluidlib$getFluid() instanceof IFluid fluid && fluid.canSwim((Entity) (Object) this)) return fluid.getTagKey();
//        return original;
//    }
}
