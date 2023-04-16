package top.offsetmonkey538.fluidlib.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.SpongeBlock;
import net.minecraft.fluid.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

/**
 * Makes sponges absorb fluids in the {@link FluidLibTags#ABSORBED_BY_SPONGE fluid-lib:absorbed_by_spong} tag.
 */
@Mixin(SpongeBlock.class)
public abstract class SpongeBlockMixin {

    @ModifyExpressionValue(
            method = "absorbWater",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/fluid/FluidState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$makeSpongeAbsorbFluidsWithCorrectTag(boolean original, @Local(ordinal = 0) FluidState fluid) {
        return original || fluid.isIn(FluidLibTags.ABSORBED_BY_SPONGE);
    }
}
