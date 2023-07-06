package top.offsetmonkey538.fluidlib.api.client.fog;

import java.awt.Color;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;

public interface IFluidFogModifier {

    /**
     * Called in the {@link net.minecraft.client.render.BackgroundRenderer#render(Camera, float, ClientWorld, int, float) BackgroundRenderer#render()} method.
     *
     * @return The color for the fog.
     */
    Color getColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness);

    /**
     * Called in the {@link net.minecraft.client.render.BackgroundRenderer#applyFog(Camera, BackgroundRenderer.FogType, float, boolean, float) BackgroundRenderer#applyFog()} method.
     * @param fogData the fog data to modify.
     * @param camera the current camera.
     * @param viewDistance the current view distance in blocks.
     * @param thickFog should the fog be thick. (Dragon fight, nether dimension)
     * @param tickDelta the tick delta.
     */
    void modifyFogData(BackgroundRenderer.FogData fogData, Camera camera, float viewDistance, boolean thickFog, float tickDelta);
}
