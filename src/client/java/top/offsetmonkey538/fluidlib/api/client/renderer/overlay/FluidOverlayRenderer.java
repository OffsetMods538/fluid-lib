package top.offsetmonkey538.fluidlib.api.client.renderer.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

@FunctionalInterface
public interface FluidOverlayRenderer {

    /**
     * Renders the overlay.
     * @param client Instance of the minecraft client.
     * @param matrices The matrix stack.
     */
    void render(MinecraftClient client, MatrixStack matrices);
}
