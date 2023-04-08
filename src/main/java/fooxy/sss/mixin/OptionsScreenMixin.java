package fooxy.sss.mixin;

import fooxy.sss.ShaderUtils;
import fooxy.sss.gui.SuperSecretSettingsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    public void init(CallbackInfo ci) {
        var secretSettingsButton = ButtonWidget.builder(Text.translatable("fooxy.sss.button"), button -> superSecretSettingsAction())
                .dimensions(this.width / 2 + 5, this.height / 6 + 36 - 18, 150, 20)
                .build();
        this.addDrawableChild(secretSettingsButton);
    }

    private void superSecretSettingsAction() {
        if(client != null) {
            if (Screen.hasShiftDown())
                client.setScreen(new SuperSecretSettingsScreen(this));
            else {
                Object[] soundIds = Registries.SOUND_EVENT.getIds().toArray();
                Identifier randomSoundId = (Identifier) soundIds[new Random().nextInt(soundIds.length)];
                assert randomSoundId != null;

                if (!randomSoundId.toString().contains("music")) {
                    var soundManager = client.getSoundManager();
                    soundManager.stopSounds(SoundEvents.UI_BUTTON_CLICK.value().getId(), SoundCategory.MASTER);
                    soundManager.play(PositionedSoundInstance.master(SoundEvent.of(randomSoundId), 1f));
                }
                if (client.getCameraEntity() != null)
                    ShaderUtils.load(true);
            }
        }
    }
}
