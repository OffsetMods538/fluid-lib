package top.offsetmonkey538.fluidlib.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.fluidlib.api.client.FluidFogModifierRegistry;
import top.offsetmonkey538.fluidlib.api.client.fog.IFluidFogModifier;
import top.offsetmonkey538.fluidlib.impl.client.FluidFogModifierRegistryImpl;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {

    @Shadow private static float red;
    @Shadow private static float green;
    @Shadow private static float blue;

    @ModifyVariable(
            method = "render",
            at = @At("STORE")
    )
    private static CameraSubmersionType fluidlib$disableUnneededVanillaCodeWhenInCustomFluid(CameraSubmersionType value, @Local(argsOnly = true) Camera camera, @Local(argsOnly = true) ClientWorld world) {
        if (value != CameraSubmersionType.NONE) return value;
        if (fluidlib$getFogModifier(camera, world) == null) return value;

        return CameraSubmersionType.LAVA;
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;"
            )
    )
    private static void fluidlib$applyFluidFogColorModifier(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
        final IFluidFogModifier fogModifier = fluidlib$getFogModifier(camera, world);
        if (fogModifier == null) return;

        Color color = fogModifier.getColor();

        red = color.getRed() / 255f;
        green = color.getGreen() / 255f;
        blue = color.getBlue() / 255f;

        //RenderSystem.setShaderFogColor(red, green, blue, 0);
    }

    @Inject(
            method = "applyFog",
            at = @At(
                    value = "TAIL"
                    //value = "INVOKE",
                    //target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"
            )
    )
    private static void fluidlib$applyFluidFogDataModifier(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, @Local(ordinal = 0) BackgroundRenderer.FogData fogData) {
        fogData.fogShape = FogShape.SPHERE;

        //// Exact copy of vanilla water fog
        //fogData.fogStart = -8.0f;
        //fogData.fogEnd = 96.0f;
        //if (camera.getFocusedEntity() instanceof ClientPlayerEntity entity) {
        //    fogData.fogEnd *= Math.max(0.25f, entity.getUnderwaterVisibility());
        //    RegistryEntry<Biome> registryEntry = entity.world.getBiome(entity.getBlockPos());
        //    if (registryEntry.isIn(BiomeTags.HAS_CLOSER_WATER_FOG)) {
        //        fogData.fogEnd *= 0.85f;
        //    }
        //}
        //if (fogData.fogEnd > viewDistance) {
        //    fogData.fogEnd = viewDistance;
        //    fogData.fogShape = FogShape.CYLINDER;
        //}

        //RenderSystem.setShaderFogStart(fogData.fogStart);
        //RenderSystem.setShaderFogEnd(fogData.fogEnd);
        //RenderSystem.setShaderFogShape(fogData.fogShape);


        final ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return;

        // Get the fog modifier for the fluid.
        final IFluidFogModifier fogModifier = fluidlib$getFogModifier(camera, world);
        if (fogModifier == null) return;

        // Apply the fog modifier
        fogModifier.modifyFogData(fogData, camera, viewDistance, thickFog, tickDelta);

        RenderSystem.setShaderFogStart(fogData.fogStart);
        RenderSystem.setShaderFogEnd(fogData.fogEnd);
        RenderSystem.setShaderFogShape(fogData.fogShape);
    }

    @Unique
    private static FluidState fluidlib$getFluidInCamera(Camera camera, BlockView world) {
        final BlockPos pos = camera.getBlockPos();
        final FluidState fluid = world.getFluidState(pos);

        return fluid;
    }

    @Unique
    private static IFluidFogModifier fluidlib$getFogModifier(Camera camera, BlockView world) {
        final FluidState fluid = fluidlib$getFluidInCamera(camera, world);
        final TagKey<Fluid> fluidTag = ((FluidFogModifierRegistryImpl) FluidFogModifierRegistry.INSTANCE).matches(fluid::isIn).findFirst().orElse(null);

        return FluidFogModifierRegistry.INSTANCE.get(fluidTag);
    }
}
