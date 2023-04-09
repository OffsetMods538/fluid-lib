package top.offsetmonkey538.fluidlib.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;
import top.offsetmonkey538.fluidlib.tag.FluidLibTags;

import java.util.concurrent.CompletableFuture;

public class DataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(FluidTagProvider::new);
    }

    private static class FluidTagProvider extends FabricTagProvider.FluidTagProvider {
        public FluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(FluidLibTags.HARDENS_CONCRETE)
                    .addOptionalTag(FluidTags.WATER);

            getOrCreateTagBuilder(FluidLibTags.KEEPS_CORAL_ALIVE)
                    .addOptionalTag(FluidTags.WATER);
        }
    }
}
