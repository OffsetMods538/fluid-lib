package top.offsetmonkey538.fluidlib.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

@Mixin(RespawnAnchorBlock.class)
public class RespawnAnchorMixin {

    @ModifyExpressionValue(
            method = "hasStillWater",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private static boolean fluidlib$makeRespawnAnchorExplosionsNotBreakBlocksWhenFluidInCorrectTag0(boolean original, @Local(ordinal = 0) FluidState fluid) {
        return original || fluid.isIn(FluidLibTags.DISABLES_RESPAWN_ANCHOR_EXPLOSION);
    }

    @ModifyExpressionValue(
            method = "explode",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private static boolean fluidlib$makeRespawnAnchorExplosionsNotBreakBlocksWhenFluidInCorrectTag1(boolean original, BlockState state, World world, final BlockPos explodedPos) {
        return original || world.getFluidState(explodedPos.up()).isIn(FluidLibTags.DISABLES_RESPAWN_ANCHOR_EXPLOSION);
    }
}
