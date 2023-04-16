package top.offsetmonkey538.fluidlib;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stores helper functions for the mod.
 */
public final class FluidLib {
    private FluidLib() {

    }

    /**
     * The id of the mod
     */
    public static final String MOD_ID = "fluid-lib";
    /**
     * A logger for the mod
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * @param path The path for the identifier
     * @return An {@link Identifier} with the {@link #MOD_ID} and provided path.
     */
    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
