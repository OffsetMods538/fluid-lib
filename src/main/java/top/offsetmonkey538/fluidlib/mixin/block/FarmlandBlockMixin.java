package top.offsetmonkey538.fluidlib.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin {

    @ModifyExpressionValue(
            method = "isWaterNearby",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private static boolean fluidlib$makeFarmlandMoistIfFluidInCorrectTag(boolean original, WorldView world, @Local(ordinal = 1) BlockPos pos) {
        return original || world.getFluidState(pos).isIn(FluidLibTags.WATERS_FARMLAND);
    }
}
