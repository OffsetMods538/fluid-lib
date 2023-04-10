package top.offsetmonkey538.fluidlib.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @ModifyExpressionValue(
            method = "renderStatusBars",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$drawOxygenBarIfFluidInCorrectTag(boolean original, @Local(ordinal = 0) PlayerEntity player) {
        return original || player.isSubmergedIn(FluidLibTags.ALLOWS_DROWNING);
    }
}
