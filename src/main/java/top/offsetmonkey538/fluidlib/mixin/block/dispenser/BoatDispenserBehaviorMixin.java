package top.offsetmonkey538.fluidlib.mixin.block.dispenser;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

@Mixin(BoatDispenserBehavior.class)
public abstract class BoatDispenserBehaviorMixin {

    @WrapOperation(
            method = "dispenseSilently",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$allowDispensingBoatsInFluidIfFluidInCorrectTag(FluidState instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        return instance.isIn(FluidLibTags.ALLOWS_BOAT_DISPENSING) || original.call(instance, tag);
    }
}
