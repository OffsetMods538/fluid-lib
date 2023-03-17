package testing.init;

import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModFluidTags {
    private ModFluidTags() {

    }

    public static final TagKey<Fluid> TESTING_FLUID = TagKey.of(RegistryKeys.FLUID, new Identifier("testmod", "testing_fluid"));
}
