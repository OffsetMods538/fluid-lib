package testing.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {
    private ModItems() {

    }


    public static final BucketItem TESTING_FLUID_BUCKET = register("testing_fluid_bucket", new BucketItem(ModFluids.STILL_TESTING_FLUID, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));


    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier("testmod", name), item);
    }

    public static void register() {

    }
}
