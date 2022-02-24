package fooxy.sss.mixin;

import fooxy.sss.ShaderUtils;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(at = @At("TAIL"), method = "onFramebufferSizeChanged")
    private void updateShaderSize(CallbackInfo ci) {
        if(ShaderUtils.enabled)
            ShaderUtils.shader.setupDimensions(ShaderUtils.client.getWindow().getFramebufferWidth(), ShaderUtils.client.getWindow().getFramebufferHeight());
    }
}
