package cn.ksmcbrigade.wso.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(SplashOverlay.class)
public class LoadingOverlayMixin {

    @Unique
    private final String MOD_ID = "wso";

    @Unique
    private int now = 1;

    @Inject(method = "render",at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableDepthTest()V",shift = At.Shift.AFTER))
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci){
        context.fill(0,0,context.getScaledWindowWidth(),context.getScaledWindowHeight(),Color.BLACK.getRGB());

        int SWidth = context.getScaledWindowWidth();
        int SHeight = context.getScaledWindowHeight();

        int width = 96;
        int height = 96;

        context.drawTexture(new Identifier(MOD_ID,"gui/icon.png"),(SWidth-width)/2,(SHeight-height)/2-height/2,0,0,width,height,width,height);
    }

    @Inject(method = "renderProgressBar",at = @At("HEAD"),cancellable = true)
    public void render(DrawContext context, int minX, int minY, int maxX, int maxY, float opacity, CallbackInfo ci){

        int SWidth = context.getScaledWindowWidth();
        int SHeight = context.getScaledWindowHeight();

        int width = 32;
        int height = 32;

        context.drawTexture(new Identifier(MOD_ID,"gui/loading/"+now+".png"),(SWidth-width)/2,(SHeight-height)/2+2*height,0,0,width,height,width,height);

        now++;

        if(now>75) now = 1;

        ci.cancel();
    }
}
