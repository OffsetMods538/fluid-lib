package top.offsetmonkey538.fluidlib.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
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
    private static CameraSubmersionType fluidlib$disableUnneededVanillaCodeWhenInCustomFluid(CameraSubmersionType value, @Local(argsOnly = true) Camera camera, @Local(argsOnly = true) ClientWorld world, @Share("originalSubmersionType") LocalRef<CameraSubmersionType> originalSubmersionType) {
        originalSubmersionType.set(value);

        if (value != CameraSubmersionType.NONE) return value;
        if (fluidlib$getFogModifier(camera, world) == null) return value;

        // By setting the return type to LAVA, we make sure that the final else
        // block in the if-else chain doesn't run. We don't *need* to do this, but
        // the code in that else block does quite a lot of math and things, as we
        // later overwrite its work anyway, there's no point in wasting performance.
        return CameraSubmersionType.LAVA;
    }

    @ModifyVariable(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;"
            )
    )
    private static CameraSubmersionType fluidlib$setOriginalCameraSubmersionType(CameraSubmersionType current, @Share("originalSubmersionType") LocalRef<CameraSubmersionType> originalSubmersionType) {
        return originalSubmersionType.get();
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;"
            )
    )
    private static void fluidlib$applyFluidFogColorModifier(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness, CallbackInfo ci) {
        // Get the fog modifier for the current fluid.
        final IFluidFogModifier fogModifier = fluidlib$getFogModifier(camera, world);
        if (fogModifier == null) return;

        Color color = fogModifier.getColor(camera, tickDelta, world, viewDistance, skyDarkness);

        red = color.getRed() / 255f;
        green = color.getGreen() / 255f;
        blue = color.getBlue() / 255f;
    }

    @ModifyVariable(
            method = "applyFog",
            at = @At(
                    value = "STORE"
            )
    )
    private static BackgroundRenderer.StatusEffectFogModifier fluidlib$storeStatusEffectFogModifierExistence(BackgroundRenderer.StatusEffectFogModifier fogModifier, @Share("hasStatusEffectFogModifier") LocalBooleanRef hasStatusEffectFogModifier) {
        hasStatusEffectFogModifier.set(fogModifier != null);

        return fogModifier;
    }

    @Inject(
            method = "applyFog",
            at = @At(
                    value = "TAIL"
                    //value = "INVOKE",
                    //target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"
            )
    )
    private static void fluidlib$applyFluidFogDataModifier(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, @Local(ordinal = 0) BackgroundRenderer.FogData fogData, @Share("hasStatusEffectFogModifier") LocalBooleanRef hasStatusEffectFogModifier) {
        if (hasStatusEffectFogModifier.get()) return;

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

        fogData.fogShape = FogShape.SPHERE;

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

        return world.getFluidState(pos);
    }

    @Unique
    private static IFluidFogModifier fluidlib$getFogModifier(Camera camera, BlockView world) {
        final FluidState fluid = fluidlib$getFluidInCamera(camera, world);
        final TagKey<Fluid> fluidTag = ((FluidFogModifierRegistryImpl) FluidFogModifierRegistry.INSTANCE).matches(fluid::isIn).findFirst().orElse(null);

        return FluidFogModifierRegistry.INSTANCE.get(fluidTag);
    }
}
