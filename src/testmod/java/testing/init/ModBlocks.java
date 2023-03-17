package testing.init;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks {
    private ModBlocks() {

    }


    public static final FluidBlock TESTING_FLUID = register("testing_fluid", new FluidBlock(ModFluids.STILL_TESTING_FLUID, FabricBlockSettings.of(Material.WATER).noCollision().strength(100.0f).dropsNothing()));


    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, new Identifier("testmod", name), block);
    }

    public static void register() {

    }
}
