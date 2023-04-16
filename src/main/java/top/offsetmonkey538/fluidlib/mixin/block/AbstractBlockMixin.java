package top.offsetmonkey538.fluidlib.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.IFluid;

/**
 * Something related to path finding through {@link IFluid custom fluid}s.
 */
@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @ModifyExpressionValue(
            method = "canPathfindThrough",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$allowPathfindingThroughCustomFluid(boolean original, BlockState state, BlockView world, BlockPos pos) {
        return original || world.getFluidState(pos).getFluid() instanceof IFluid;
    }
}
