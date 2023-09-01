package fooxy.sss.mixin;

import fooxy.sss.ShaderUtils;
import fooxy.sss.gui.SuperSecretSettingsScreen;
import net.minecraft.client.MinecraftClient;
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
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    public void init(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("fooxy.sss.button"), button -> superSecretSettingsAction()).dimensions(this.width / 2 + 5, this.height / 6 + 36 - 6, 150, 20).build());
    }

    private void superSecretSettingsAction() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (Screen.hasShiftDown())
            client.setScreen(new SuperSecretSettingsScreen(this));
        else {
            Object[] ids = Registries.SOUND_EVENT.getIds().toArray();
            Identifier se = (Identifier) ids[new Random().nextInt(ids.length)];
            assert se != null;
            if (!se.toString().contains("music")) {
                client.getSoundManager().stopSounds(SoundEvents.UI_BUTTON_CLICK.value().getId(), SoundCategory.MASTER);
                client.getSoundManager().play(PositionedSoundInstance.master(SoundEvent.of(se), 1.0f));
            }
            if (client != null && client.getCameraEntity() != null)
                ShaderUtils.load(true);
        }
    }

    @ModifyConstant(method = "init", constant = @Constant(intValue = 26))
    private int injected48(int value) {
        return value + 12;
    }

}
