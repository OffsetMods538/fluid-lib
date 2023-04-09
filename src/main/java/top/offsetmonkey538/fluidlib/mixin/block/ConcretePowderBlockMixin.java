package top.offsetmonkey538.fluidlib.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConcretePowderBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

@Mixin(ConcretePowderBlock.class)
public abstract class ConcretePowderBlockMixin {

    @ModifyReturnValue(
            method = "hardensIn",
            at = @At("RETURN")
    )
    private static boolean fluidlib$makeConcreteHardenIfFluidInHardensConcreteTag(boolean original, BlockState state) {
        return original || state.getFluidState().isIn(FluidLibTags.HARDENS_CONCRETE);
    }
}
