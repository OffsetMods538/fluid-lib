package top.offsetmonkey538.fluidlib;

import net.minecraft.util.Identifier;

public final class FluidLib {
    private FluidLib() {

    }

    public static final String MOD_ID = "fluid-lib";

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}