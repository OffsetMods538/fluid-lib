package top.offsetmonkey538.fluidlib.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.fluidlib.api.client.FluidFogModifierRegistry;
import top.offsetmonkey538.fluidlib.api.client.fog.IFluidFogModifier;
import top.offsetmonkey538.fluidlib.impl.client.FluidFogModifierRegistryImpl;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;getLevelProperties()Lnet/minecraft/client/world/ClientWorld$Properties;"
            )
    )
    private static void fluidlib$applyFluidFogColorModifier(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
        final TagKey<Fluid> fluid = ((FluidFogModifierRegistryImpl) FluidFogModifierRegistry.INSTANCE).matches(tag -> fluidlib$isCameraInFluid(camera, world, tag)).findFirst().orElse(null);
        final IFluidFogModifier fogModifier = FluidFogModifierRegistry.INSTANCE.get(fluid);
        if (fogModifier == null) return;

        fogModifier.modifyColor();
    }

    @Inject(
            method = "applyFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"
            )
    )
    private static void fluidlib$applyFluidFogDataModifier(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, @Local(ordinal = 0) BackgroundRenderer.FogData fogData) {
        final ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return;

        final TagKey<Fluid> fluid = ((FluidFogModifierRegistryImpl) FluidFogModifierRegistry.INSTANCE).matches(tag -> fluidlib$isCameraInFluid(camera, world, tag)).findFirst().orElse(null);
        final IFluidFogModifier fogModifier = FluidFogModifierRegistry.INSTANCE.get(fluid);
        if (fogModifier == null) return;

        fogModifier.modifyFogData(fogData, camera, viewDistance, thickFog, tickDelta);
    }

    @Unique
    private static boolean fluidlib$isCameraInFluid(Camera camera, BlockView world, TagKey<Fluid> fluidTag) {
        final BlockPos pos = camera.getBlockPos();
        final FluidState fluid = world.getFluidState(pos);
        if (!fluid.isIn(fluidTag)) return false;

        final float fluidHeight = pos.getY() + fluid.getHeight(world, pos);
        return camera.getPos().y < fluidHeight;
    }
}
