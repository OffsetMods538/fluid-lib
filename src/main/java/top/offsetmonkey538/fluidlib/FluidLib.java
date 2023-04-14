package top.offsetmonkey538.fluidlib;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FluidLib {
    private FluidLib() {

    }

    public static final String MOD_ID = "fluid-lib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
