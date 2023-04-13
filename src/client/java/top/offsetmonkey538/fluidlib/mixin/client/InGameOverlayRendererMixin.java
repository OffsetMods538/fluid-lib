package top.offsetmonkey538.fluidlib.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.fluidlib.IFluid;
import top.offsetmonkey538.fluidlib.api.client.renderer.FluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.api.client.FluidOverlayRendererRegistry;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @Inject(
            method = "renderOverlays",
            at = @At("TAIL")
    )
    private static void fluidlib$renderCustomFluidOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        final ClientWorld world = client.world;
        final ClientPlayerEntity player = client.player;
        if (world == null) throw new IllegalStateException("Tried rendering fluid overlay, but world is null.");
        if (player == null) throw new IllegalStateException("Tried rendering fluid overlay, but player is null.");

        final FluidState fluidState = world.getFluidState(new BlockPos(player.getEyePos()));
        if (!(fluidState.getFluid() instanceof IFluid fluid) || !player.isSubmergedIn(fluid.getTagKey())) return;

        final FluidOverlayRenderer renderer = FluidOverlayRendererRegistry.INSTANCE.get(fluidState.getFluid());
        if (renderer == null) return;


        renderer.render(client, matrices);
    }
}
