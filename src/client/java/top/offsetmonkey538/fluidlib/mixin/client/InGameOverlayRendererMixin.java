package top.offsetmonkey538.fluidlib.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.fluidlib.api.client.FluidOverlayRendererRegistry;
import top.offsetmonkey538.fluidlib.api.client.renderer.overlay.FluidOverlayRenderer;
import top.offsetmonkey538.fluidlib.impl.client.FluidOverlayRendererRegistryImpl;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

    @Inject(
            method = "renderOverlays",
            at = @At("TAIL")
    )
    private static void fluidlib$renderCustomFluidOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        if (client.player == null) throw new IllegalStateException("Tried rendering fluid overlay but player is null!");

        final TagKey<Fluid> fluid = ((FluidOverlayRendererRegistryImpl) FluidOverlayRendererRegistry.INSTANCE).matches(client.player::isSubmergedIn).findFirst().orElse(null);
        final FluidOverlayRenderer renderer = FluidOverlayRendererRegistry.INSTANCE.get(fluid);
        if (renderer != null) renderer.render(client, matrices);
    }
}
