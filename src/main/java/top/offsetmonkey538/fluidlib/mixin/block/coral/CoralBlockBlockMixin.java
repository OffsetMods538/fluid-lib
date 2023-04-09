package top.offsetmonkey538.fluidlib.mixin.block.coral;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.CoralBlockBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

@Mixin(CoralBlockBlock.class)
public abstract class CoralBlockBlockMixin {

    @ModifyExpressionValue(
            method = "isInWater",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private static boolean fluidlib$makeCoralSurviveInFluidInCorrectTag(boolean original, BlockView world, BlockPos pos, @Local(ordinal = 0) Direction direction) {
        return original || world.getFluidState(pos.offset(direction)).isIn(FluidLibTags.KEEPS_CORAL_ALIVE);
    }
}
