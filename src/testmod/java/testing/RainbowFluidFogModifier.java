package testing;

import java.awt.Color;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import top.offsetmonkey538.fluidlib.api.client.fog.IFluidFogModifier;

public class RainbowFluidFogModifier implements IFluidFogModifier {
    private float distanceValue = 0;
    private float colorValue = 0;

    @Override
    public Color getColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness) {
        colorValue += 0.01f * tickDelta;

        float hue = MathHelper.sin(colorValue);

        colorValue += 0.01f * tickDelta;

        return Color.getHSBColor(hue, 1f, 0.5f);
    }

    @Override
    public void modifyFogData(BackgroundRenderer.FogData fogData, Camera camera, float viewDistance, boolean thickFog, float tickDelta) {
        fogData.fogStart = -8f;


        distanceValue += 0.02f * tickDelta;

        fogData.fogEnd = Math.max(MathHelper.sin(distanceValue) * 48f, 4f);

        distanceValue += 0.02f * tickDelta;
    }
}
