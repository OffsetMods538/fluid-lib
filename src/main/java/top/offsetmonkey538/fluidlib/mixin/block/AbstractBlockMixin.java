package top.offsetmonkey538.fluidlib.mixin.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.AbstractBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;
import top.offsetmonkey538.fluidlib.api.IFluidBehaviour;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @WrapOperation(
            method = "canPathfindThrough",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$allowPathfindingThroughCustomFluid(FluidState instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        IFluidBehaviour behaviour = FluidBehaviourRegistry.INSTANCE.get(instance);
        if (behaviour == null || !instance.isIn(behaviour.getTagKey())) return original.call(instance, tag);

        return true;
    }
}
