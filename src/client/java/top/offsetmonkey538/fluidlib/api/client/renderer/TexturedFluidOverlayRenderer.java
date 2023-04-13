package top.offsetmonkey538.fluidlib.api.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;

/**
 * {@link FluidOverlayRenderer} using a texture.
 */
public class TexturedFluidOverlayRenderer implements FluidOverlayRenderer {
    private final Identifier texture;

    /**
     * {@link FluidOverlayRenderer} using a texture.
     * @param texture The texture to use.
     */
    public TexturedFluidOverlayRenderer(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public void render(MinecraftClient client, MatrixStack matrices) {
        final ClientPlayerEntity player = client.player;
        if (player == null || player.isSpectator()) return;

        final float brightness = LightmapTextureManager.getBrightness(player.world.getDimension(), player.world.getLightLevel(new BlockPos(player.getEyePos())));


        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.enableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(brightness, brightness, brightness, 0.2f);
        RenderSystem.setShaderTexture(0, texture);


        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        float yawTextureOffset = -player.getYaw() / 64.0f;
        float pitchTextureOffset = player.getPitch() / 64.0f;

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(positionMatrix, -1.0f, -1.0f, -0.5f).texture(4.0f + yawTextureOffset, 4.0f + pitchTextureOffset).next();
        bufferBuilder.vertex(positionMatrix, 1.0f, -1.0f, -0.5f).texture(0.0f + yawTextureOffset, 4.0f + pitchTextureOffset).next();
        bufferBuilder.vertex(positionMatrix, 1.0f, 1.0f, -0.5f).texture(0.0f + yawTextureOffset, 0.0f + pitchTextureOffset).next();
        bufferBuilder.vertex(positionMatrix, -1.0f, 1.0f, -0.5f).texture(4.0f + yawTextureOffset, 0.0f + pitchTextureOffset).next();

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());


        RenderSystem.disableBlend();
    }
}
