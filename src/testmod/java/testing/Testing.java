package testing;

import java.awt.Color;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import testing.init.ModBlocks;
import testing.init.ModFluidTags;
import testing.init.ModFluids;
import testing.init.ModItems;
import top.offsetmonkey538.fluidlib.api.client.FluidFogModifierRegistry;
import top.offsetmonkey538.fluidlib.api.client.FluidOverlayRendererRegistry;
import top.offsetmonkey538.fluidlib.api.client.fog.IFluidFogModifier;
import top.offsetmonkey538.fluidlib.api.client.fog.SimpleFluidFogModifier;
import top.offsetmonkey538.fluidlib.api.client.overlay.*;
import top.offsetmonkey538.fluidlib.api.FluidBehaviourRegistry;

public class Testing implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        ModFluids.register();
        ModBlocks.register();
        ModItems.register();

        FluidBehaviourRegistry.INSTANCE.register(ModFluids.STILL_TESTING_FLUID, ModFluids.FLOWING_TESTING_FLUID, new TestingFluidBehaviour());
    }

    @Override
    public void onInitializeClient() {
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_TESTING_FLUID, ModFluids.FLOWING_TESTING_FLUID, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                0x79cd3c
        ));

        //FluidOverlayRendererRegistry.INSTANCE.register(ModFluidTags.TESTING_FLUID, new TexturedFluidOverlayRenderer(
        //        new Identifier("textures/block/oak_log.png")
        //));
        FluidOverlayRendererRegistry.INSTANCE.register(ModFluidTags.TESTING_FLUID, new SolidColorFluidOverlayRenderer(
                0x6479cd3c
        ));

        // Rainbow stuff: FluidFogModifierRegistry.INSTANCE.register(ModFluidTags.TESTING_FLUID, new IFluidFogModifier() {
        // Rainbow stuff:     private float distanceValue = 0;
        // Rainbow stuff:     private float colorValue = 0;

        // Rainbow stuff:     @Override
        // Rainbow stuff:     public Color getColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness) {
        // Rainbow stuff:         colorValue += 0.01f * tickDelta;

        // Rainbow stuff:         float hue = MathHelper.sin(colorValue);

        // Rainbow stuff:         colorValue += 0.01f * tickDelta;

        // Rainbow stuff:         return Color.getHSBColor(hue, 1f, 0.5f);
        // Rainbow stuff:     }

        // Rainbow stuff:     @Override
        // Rainbow stuff:     public void modifyFogData(BackgroundRenderer.FogData fogData, Camera camera, float viewDistance, boolean thickFog, float tickDelta) {
        // Rainbow stuff:         fogData.fogStart = -8f;


        // Rainbow stuff:         distanceValue += 0.02f * tickDelta;

        // Rainbow stuff:         fogData.fogEnd = Math.max(MathHelper.sin(distanceValue) * 48f, 4f);

        // Rainbow stuff:         distanceValue += 0.02f * tickDelta;
        // Rainbow stuff:     }
        // Rainbow stuff: });

        FluidFogModifierRegistry.INSTANCE.register(ModFluidTags.TESTING_FLUID, new SimpleFluidFogModifier(
                0.5f,
                121,
                205,
                60,
                -8f,
                24f
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.STILL_TESTING_FLUID, ModFluids.FLOWING_TESTING_FLUID);
    }
}
