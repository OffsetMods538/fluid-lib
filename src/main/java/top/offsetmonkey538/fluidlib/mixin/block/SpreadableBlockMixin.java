package top.offsetmonkey538.fluidlib.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

/**
 * Disables grass, mycelium, etc. spreading if the above fluid is in the {@link FluidLibTags#DISABLES_SPREADING fluid-lib:disables_spreading} tag.
 */
@Mixin(SpreadableBlock.class)
public abstract class SpreadableBlockMixin {

    @ModifyExpressionValue(
            method = "canSpread",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private static boolean fluidlib$disableSpreadingWhenAboveFluidInCorrectTag(boolean original, BlockState state, WorldView world, BlockPos pos) {
        return original || world.getFluidState(pos.up()).isIn(FluidLibTags.DISABLES_SPREADING);
    }
}
