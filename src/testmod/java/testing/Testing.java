package testing;

import net.fabricmc.api.ModInitializer;
import testing.init.ModBlocks;
import testing.init.ModFluids;
import testing.init.ModItems;

public class Testing implements ModInitializer {

    @Override
    public void onInitialize() {
        ModFluids.register();
        ModBlocks.register();
        ModItems.register();
    }
}
