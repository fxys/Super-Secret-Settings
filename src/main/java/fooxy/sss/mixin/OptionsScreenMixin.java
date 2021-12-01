package fooxy.sss.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * <p>OptionScreenMixin - creates a button in the Options Menu which behaves like the "Super Secret Settings..." feature removed in 1.9</p>
 */
@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    private static final Logger LOGGER = LogManager.getLogger("sss");

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    /**
     * <p>Creates the "Super Secret Settings..." button over the "Music & Sounds" button.</p>
     * @param ci CallbackInfo, not used.
     */
    @Inject(at = @At("TAIL"), method = "init")
    public void init(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 36 - 6, 150, 20, new TranslatableText("fooxy.sss.button"), button -> superSecretSettingsAction()));
    }

    /**
     * <p>Tries, to play a random Sound Event instead of the UI Click.</p>
     * <p>A shader is applied if the player is in a game.</p>
     * <p>The order of the shaders is not random.</p>
     */
    private void superSecretSettingsAction() {
        MinecraftClient client = MinecraftClient.getInstance();
        try {
            SoundEvent se = Registry.SOUND_EVENT.getRandom(new Random());
            assert se != null;
            if (!se.getId().toString().contains("music")) {
                suppressSound(client, SoundEvents.UI_BUTTON_CLICK.getId());
                client.getSoundManager().play(PositionedSoundInstance.master(se, 1.0f));
            } else {
                return;
            }
        } catch (Exception e) {
            LOGGER.warn("Error while playing sound!");
        }
        if (client != null && client.getCameraEntity() != null) {
            client.gameRenderer.loadForcedShader();
        }
    }

    /**
     * <p>Stops the specified sound from playing.</p>
     * @param mc Minecraft Client
     * @param id Sound Identifier
     */
    private void suppressSound(MinecraftClient mc, Identifier id) {
        mc.getSoundManager().stopSounds(id, SoundCategory.MASTER);
    }

    /**
     * <p>Modifies the 'y' values for each ButtonWidget to create more space in the option menu; Each ButtonWidget is moved downwards by 12.</p>
     *
     * **Please tell me if there is a better method for doing this.
     */
    @ModifyConstant(method = "init", constant = @Constant(intValue = 48))
    private int injected48(int value) {
        return value + 12;
    }
    @ModifyConstant(method = "init", constant = @Constant(intValue = 72))
    private int injected72(int value) {
        return value + 12;
    }
    @ModifyConstant(method = "init", constant = @Constant(intValue = 96))
    private int injected96(int value) {
        return value + 12;
    }
    @ModifyConstant(method = "init", constant = @Constant(intValue = 120))
    private int injected120(int value) {
        return value + 12;
    }
}
