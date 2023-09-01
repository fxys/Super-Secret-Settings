package fooxy.sss;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class Keybinds implements ModInitializer {

    @Override
    public void onInitialize() {
        KeyBinding toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("fooxy.sss.key.toggle", InputUtil.UNKNOWN_KEY.getCode(), "fooxy.sss.button"));
        KeyBinding nextKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("fooxy.sss.key.next", InputUtil.UNKNOWN_KEY.getCode(), "fooxy.sss.button"));
        KeyBinding prevKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("fooxy.sss.key.prev", InputUtil.UNKNOWN_KEY.getCode(), "fooxy.sss.button"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                if (toggleKey.wasPressed()) {
                    ShaderUtils.enabled ^= true;
                    if (ShaderUtils.shader != null)
                        ShaderUtils.shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
                    client.player.sendMessage(Text.translatable("fooxy.sss.button." + ShaderUtils.enabled), true);
                } else if (nextKey.wasPressed())
                    ShaderUtils.load(true);
                else if (prevKey.wasPressed())
                    ShaderUtils.load(false);
            }
        });
    }
}
