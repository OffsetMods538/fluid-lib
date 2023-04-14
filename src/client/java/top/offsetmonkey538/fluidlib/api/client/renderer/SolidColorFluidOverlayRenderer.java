package top.offsetmonkey538.fluidlib.api.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;

/**
 * {@link IFluidOverlayRenderer} using a solid color.
 */
public class SolidColorFluidOverlayRenderer implements IFluidOverlayRenderer {
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    /**
     * {@link IFluidOverlayRenderer} using a solid color.
     * @param argb The alpha, red, green and blue components of the overlay. 0xAARRGGBB
     */
    public SolidColorFluidOverlayRenderer(int argb) {
        this(
                (argb >> 16) & 0xFF,
                (argb >> 8) & 0xFF,
                argb & 0xFF,
                (argb >> 24) & 0xFF
        );
    }
    /**
     * {@link IFluidOverlayRenderer} using a solid color.
     * @param red The red component of the overlay. From 0 to 255.
     * @param green The green component of the overlay. From 0 to 255.
     * @param blue The blue component of the overlay. From 0 to 255.
     * @param alpha The alpha component of the overlay. From 0 to 255.
     */
    public SolidColorFluidOverlayRenderer(float red, float green, float blue, float alpha) {
        this.red   = red   / 255.0f;
        this.green = green / 255.0f;
        this.blue  = blue  / 255.0f;
        this.alpha = alpha / 255.0f;
    }

    @Override
    public void render(MinecraftClient client, MatrixStack matrices) {
        final ClientPlayerEntity player = client.player;
        if (player == null || player.isSpectator()) return;

        final float brightness = LightmapTextureManager.getBrightness(player.world.getDimension(), player.world.getLightLevel(new BlockPos(player.getEyePos())));


        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.setShaderColor(brightness, brightness, brightness, alpha);


        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, -0.5f).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, -0.5f).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, -0.5f).color(red, green, blue, alpha).next();
        bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, -0.5f).color(red, green, blue, alpha).next();

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());

        RenderSystem.disableBlend();
    }

    @Override
    public String toString() {
        return "SolidColorFluidOverlayRenderer[red=%s,green=%s,blue=%s,alpha=%s]".formatted(red * 255f, green * 255f, blue * 255f, alpha * 255f);
    }
}
