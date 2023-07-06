package top.offsetmonkey538.fluidlib.api.client.fog;

import java.awt.Color;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;

/**
 * Simple {@link IFluidFogModifier IFluidFogModifier} that specifies RGB values and a start and end for the fog.
 */
public class SimpleFluidFogModifier implements IFluidFogModifier {
    private final int red;
    private final int green;
    private final int blue;

    private final float fogStart;
    private final float fogEnd;


    // TODO: Make sure that the fog start and end is actually in blocks!
    /**
     * @param rgb The red, green and blue components of the color. 0xRRGGBB
     * @param fogStart The start of the fog in blocks.
     * @param fogEnd The end of the fog in blocks.
     */
    public SimpleFluidFogModifier(int rgb, float fogStart, float fogEnd) {
        this(1, rgb, fogStart, fogEnd);
    }

    /**
     * @param brightness Brightness from 0 to 1. Red, green and blue are multiplied by this value.
     * @param rgb The red, green and blue components of the color. 0xRRGGBB
     * @param fogStart The start of the fog in blocks.
     * @param fogEnd The end of the fog in blocks.
     */
    public SimpleFluidFogModifier(float brightness, int rgb, float fogStart, float fogEnd) {
        this(
                brightness,
                (rgb >> 16) & 0xFF,
                (rgb >> 8) & 0xFF,
                rgb & 0xFF,
                fogStart,
                fogEnd
        );
    }

    /**
     * @param red Red component of the color from 0 to 255.
     * @param green Green component of the color from 0 to 255.
     * @param blue Blue component of the color from 0 to 255.
     * @param fogStart The start of the fog in blocks.
     * @param fogEnd The end of the fog in blocks.
     */
    public SimpleFluidFogModifier(int red, int green, int blue, float fogStart, float fogEnd) {
        this(1, red, green, blue, fogStart, fogEnd);
    }

    /**
     * @param brightness Brightness from 0 to 1. Red, green and blue are multiplied by this value.
     * @param red Red component of the color from 0 to 255.
     * @param green Green component of the color from 0 to 255.
     * @param blue Blue component of the color from 0 to 255.
     * @param fogStart The start of the fog in blocks.
     * @param fogEnd The end of the fog in blocks.
     */
    public SimpleFluidFogModifier(float brightness, int red, int green, int blue, float fogStart, float fogEnd) {
        this.red   = (int) ((red  ) * brightness);
        this.green = (int) ((green) * brightness);
        this.blue  = (int) ((blue ) * brightness);

        this.fogStart = fogStart;
        this.fogEnd   = fogEnd;
    }

    @Override
    public Color getColor(Camera camera, float tickDelta, ClientWorld world, int viewDistance, float skyDarkness) {
        return new Color(red, green, blue);
    }

    @Override
    public void modifyFogData(BackgroundRenderer.FogData fogData, Camera camera, float viewDistance, boolean thickFog, float tickDelta) {
        fogData.fogStart = fogStart;
        fogData.fogEnd = fogEnd;
        if (fogData.fogEnd > viewDistance) {
            fogData.fogEnd = viewDistance;
            fogData.fogShape = FogShape.CYLINDER;
        }

        //fogData.fogShape = FogShape.SPHERE;
        //fogData.fogStart = fogStart;
        //fogData.fogEnd = fogEnd;
        //if (fogData.fogEnd > viewDistance) {
        //    fogData.fogEnd = viewDistance;
        //    fogData.fogShape = FogShape.CYLINDER;
        //}
    }
    @Override
    public String toString() {
        return "SimpleFluidFogModifier[red=%s,green=%s,blue=%s,fogStart=%s,fogEnd=%s]".formatted(red, green, blue, fogStart, fogEnd);
    }
}
