package testing;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import testing.init.ModBlocks;
import testing.init.ModFluids;
import testing.init.ModItems;
import top.offsetmonkey538.fluidlib.api.client.FluidOverlayRendererRegistry;
import top.offsetmonkey538.fluidlib.api.client.renderer.SolidColorFluidOverlayRenderer;

public class Testing implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        ModFluids.register();
        ModBlocks.register();
        ModItems.register();
    }

    @Override
    public void onInitializeClient() {
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_TESTING_FLUID, ModFluids.FLOWING_TESTING_FLUID, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                0x79cd3c
        ));

        //FluidOverlayRendererRegistry.INSTANCE.register(ModFluids.STILL_TESTING_FLUID, ModFluids.FLOWING_TESTING_FLUID, new TexturedFluidOverlayRenderer(
        //        new Identifier("textures/block/oak_log.png")
        //));
        FluidOverlayRendererRegistry.INSTANCE.register(ModFluids.STILL_TESTING_FLUID, ModFluids.FLOWING_TESTING_FLUID, new SolidColorFluidOverlayRenderer(
                0x6479cd3c
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.STILL_TESTING_FLUID, ModFluids.FLOWING_TESTING_FLUID);
    }
}
