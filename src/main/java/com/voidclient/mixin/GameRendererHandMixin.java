package com.voidclient.mixin;

import com.voidclient.VoidConfig;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Przesuwa i skaluje widok reki/trzymanego przedmiotu (tzw. "view model").
 * Celowo bez zadnej rotacji - tylko skala i pozycja, zgodnie z zyczeniem.
 * require = 0: jesli mapowanie metody sie zmieni w przyszlej wersji gry,
 * tylko ta jedna funkcja przestanie dzialac, a reszta moda dziala dalej.
 */
@Mixin(GameRenderer.class)
public class GameRendererHandMixin {
    @Inject(method = "renderHand", at = @At("HEAD"), require = 0)
    private void voidclient$viewModel(MatrixStack matrices, Camera camera, float tickDelta, CallbackInfo ci) {
        VoidConfig.ViewModelSettings v = VoidConfig.data.viewModel;
        if (!v.enabled || v.isNeutral()) return;
        matrices.translate(v.offsetX, v.offsetY, v.offsetZ);
        matrices.scale(v.scale, v.scale, v.scale);
    }
}
