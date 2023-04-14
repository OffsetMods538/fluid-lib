package top.offsetmonkey538.fluidlib.api.client.fog;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.FogShape;
import top.offsetmonkey538.fluidlib.mixin.client.accessor.BackgroundRendererAccessor;

/**
 * Simple {@link IFluidFogModifier IFluidFogModifier} that specifies RGB values and a start and end for the fog.
 */
public class SimpleFluidFogModifier implements IFluidFogModifier {
    private final float red;
    private final float green;
    private final float blue;

    private final float fogStart;
    private final float fogEnd;


    // TODO: Make sure that the fog start and end is actually in blocks!
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
     * @param brightness Brightness from 0 to 1. Red, green and blue are multiplied by this value.
     * @param red Red component of the color from 0 to 255.
     * @param green Green component of the color from 0 to 255.
     * @param blue Blue component of the color from 0 to 255.
     * @param fogStart The start of the fog in blocks.
     * @param fogEnd The end of the fog in blocks.
     */
    public SimpleFluidFogModifier(float brightness, int red, int green, int blue, float fogStart, float fogEnd) {
        this.red   = (red   / 255f) * brightness;
        this.green = (green / 255f) * brightness;
        this.blue  = (blue  / 255f) * brightness;

        this.fogStart = fogStart;
        this.fogEnd   = fogEnd;
    }

    @Override
    public void modifyColor() {
        BackgroundRendererAccessor.setRed(red);
        BackgroundRendererAccessor.setGreen(green);
        BackgroundRendererAccessor.setBlue(blue);
    }

    @Override
    public void modifyFogData(BackgroundRenderer.FogData fogData, float viewDistance) {
        fogData.fogStart = fogStart;
        fogData.fogEnd = fogEnd;
        if (fogEnd > viewDistance) {
            fogData.fogEnd = viewDistance;
            fogData.fogShape = FogShape.CYLINDER;
        }
    }

    @Override
    public String toString() {
        return "SimpleFluidFogModifier[red=%s,green=%s,blue=%s,fogStart=%s,fogEnd=%s]".formatted(red * 255f, green * 255f, blue * 255f, fogStart, fogEnd);
    }
}
