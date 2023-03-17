package testing.init;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import testing.TestingFluid;

public final class ModFluids {
    private ModFluids() {

    }


    public static final TestingFluid STILL_TESTING_FLUID   = register("testing_fluid",         new TestingFluid.Still());
    public static final TestingFluid FLOWING_TESTING_FLUID = register("flowing_testing_fluid", new TestingFluid.Flowing());


    private static <T extends Fluid> T register(String name, T fluid) {
        return Registry.register(Registries.FLUID, new Identifier("testmod", name), fluid);
    }

    public static void register() {

    }
}
