package top.offsetmonkey538.fluidlib.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.fluidlib.api.client.FluidOverlayRendererRegistry;
import top.offsetmonkey538.fluidlib.api.client.overlay.IFluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.impl.client.FluidOverlayRendererRegistryImpl;

import static top.offsetmonkey538.fluidlib.FluidLib.LOGGER;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @Inject(
            method = "renderOverlays",
            at = @At("TAIL")
    )
    private static void fluidlib$renderCustomFluidOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (client.player == null) {
            LOGGER.warn("Tried rendering fluid overlay but player is null!");
            return;
        }
        if (client.world == null) {
            LOGGER.warn("Tried rendering fluid overlay but world is null!");
            return;
        }

        final TagKey<Fluid> fluid = ((FluidOverlayRendererRegistryImpl) FluidOverlayRendererRegistry.INSTANCE).matches(fluidTag -> fluidlib$isPlayerInFluid(client.player, client.world, fluidTag)).findFirst().orElse(null);
        final IFluidOverlayRenderer renderer = FluidOverlayRendererRegistry.INSTANCE.get(fluid);
        if (renderer != null) renderer.render(client, matrices);
    }

    @Unique
    private static boolean fluidlib$isPlayerInFluid(ClientPlayerEntity player, ClientWorld world, TagKey<Fluid> fluidTag) {
        final BlockPos pos = new BlockPos(player.getEyePos());
        final FluidState fluid = world.getFluidState(pos);

        if (!fluid.isIn(fluidTag)) return false;

        final float fluidHeight = pos.getY() + fluid.getHeight(world, pos);
        return player.getEyeY() < fluidHeight;
    }
}
