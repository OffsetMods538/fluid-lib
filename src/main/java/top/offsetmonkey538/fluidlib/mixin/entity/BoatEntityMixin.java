package top.offsetmonkey538.fluidlib.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

/**
 * Makes boats work on fluids with the {@link FluidLibTags#ALLOWS_BOATS fluid-lib:allows_boats} tag.
 */
@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
    private BoatEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyExpressionValue(
            method = "getWaterHeightBelow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$makeBoatsWorkOnFluidInCorrectTag0(boolean original, @Local(ordinal = 0) FluidState fluid) {
        return original || fluid.isIn(FluidLibTags.ALLOWS_BOATS);
    }

    @ModifyExpressionValue(
            method = "checkBoatInWater",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$makeBoatsWorkOnFluidInCorrectTag1(boolean original, @Local(ordinal = 0) FluidState fluid) {
        return original || fluid.isIn(FluidLibTags.ALLOWS_BOATS);
    }

    @ModifyExpressionValue(
            method = "getUnderWaterLocation",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$makeBoatsWorkOnFluidInCorrectTag2(boolean original, @Local(ordinal = 0) FluidState fluid) {
        return original || fluid.isIn(FluidLibTags.ALLOWS_BOATS);
    }

    @ModifyExpressionValue(
            method = "canAddPassenger",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/vehicle/BoatEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$makeBoatsWorkOnFluidInCorrectTag3(boolean original) {
        return original || this.isSubmergedIn(FluidLibTags.ALLOWS_BOATS);
    }

    @WrapOperation(
            method = "fall",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$makeBoatsWorkOnFluidInCorrectTag4(FluidState instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        return original.call(instance, tag) || instance.isIn(FluidLibTags.ALLOWS_BOATS);
    }
}
