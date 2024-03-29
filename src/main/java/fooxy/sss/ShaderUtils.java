package fooxy.sss;

import fooxy.sss.gui.ShaderList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;

import java.io.IOException;

public class ShaderUtils {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static PostEffectProcessor shader;
    public static boolean enabled = false;

    private static PostEffectProcessor getCurrent(boolean d) {
        ShaderList s;
        if(d)
            s = ShaderList.next();
        else
            s = ShaderList.previous();
        if(s.getId() == -1)
            return null;
        else {
            try {
                return new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), s.getResource());
            } catch (IOException e) {
                return null;
            }
        }
    }

    public static void load(boolean d) {
        if(shader != null)
            shader.close();
        shader = getCurrent(d);
        if(shader != null) {
            shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            enabled = true;
            return;
        }
        enabled = false;
    }
}
