package top.offsetmonkey538.fluidlib.api.client.fog;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import top.offsetmonkey538.fluidlib.mixin.client.accessor.BackgroundRendererAccessor;

public interface IFluidFogModifier {

    /**
     * Called in the {@link net.minecraft.client.render.BackgroundRenderer#render(Camera, float, ClientWorld, int, float) BackgroundRenderer#render()} method.
     * <br />
     * Use {@link BackgroundRendererAccessor} to modify the color variables.
     */
    void modifyColor();

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
