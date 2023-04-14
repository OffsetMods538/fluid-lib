package top.offsetmonkey538.fluidlib.mixin.client.accessor;

import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BackgroundRenderer.class)
public interface BackgroundRendererAccessor {
    @Accessor("red")
    static void setRed(float red) {

    }

    @Accessor("green")
    static void setGreen(float green) {

    }

    @Accessor("blue")
    static void setBlue(float blue) {

    }


    @Accessor
    static float getRed() {
        return 0;
    }

    @Accessor
    static float getGreen() {
        return 0;
    }

    @Accessor
    static float getBlue() {
        return 0;
    }
}
