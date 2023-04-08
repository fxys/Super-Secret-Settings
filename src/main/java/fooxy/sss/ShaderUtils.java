package fooxy.sss;

import fooxy.sss.gui.ShaderList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ShaderUtils {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static PostEffectProcessor shader;
    public static boolean enabled = false;

    @Nullable
    private static PostEffectProcessor getCurrent(boolean nextKeyPressed) {
        ShaderList shaderList;
        if(nextKeyPressed)
            shaderList = ShaderList.next();
        else
            shaderList = ShaderList.previous();

        if(shaderList.getId() == -1)
            return null;
        else {
            try {
                return new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), shaderList.getResource());
            } catch (IOException e) {
                return null;
            }
        }
    }

    public static void load(boolean nextKeyPressed) {
        if(shader != null)
            shader.close();
        shader = getCurrent(nextKeyPressed);

        if(shader != null) {
            shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            enabled = true;
            return;
        }
        enabled = false;
    }
}
