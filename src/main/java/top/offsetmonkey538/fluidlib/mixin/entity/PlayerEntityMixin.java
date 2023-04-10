package top.offsetmonkey538.fluidlib.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    private PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

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
