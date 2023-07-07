package top.offsetmonkey538.fluidlib.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

/**
 * Deactivates turtle helmet if in a fluid with the {@link FluidLibTags#ALLOWS_DROWNING fluid-lib:allows_drowning} tag.
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends EntityMixin {

    @ModifyExpressionValue(
            method = "updateTurtleHelmet",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;isSubmergedIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            )
    )
    private boolean fluidlib$deactivateTurtleHelmetIfFluidInCorrectTag(boolean original) {
        return original || this.isSubmergedIn(FluidLibTags.ALLOWS_DROWNING);
    }
}
