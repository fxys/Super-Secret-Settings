package fooxy.sss.gui;

import fooxy.sss.ShaderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SuperSecretSettingsScreen extends Screen {
    private final Screen parent;
    private static ButtonWidget toggleShader;
    private static CyclingButtonWidget<ShaderList> cycleShader;

    public SuperSecretSettingsScreen(Screen parent) {
        super(Text.translatable("fooxy.sss.button"));
        this.parent = parent;
    }

    @Override
    protected void init() {

        this.addDrawableChild(new TexturedButtonWidget(this.width / 2 + 101, this.height / 6 + 41, 12, 18, 0 , 207, new Identifier("textures/gui/recipe_book.png"), button -> changeShader(true)));
        this.addDrawableChild(new TexturedButtonWidget(this.width / 2 - 114, this.height / 6 + 41, 12, 18, 14 , 207, new Identifier("textures/gui/recipe_book.png"), button -> changeShader(false)));
        toggleShader = ButtonWidget.builder(Text.translatable("fooxy.sss.button." + ShaderUtils.enabled), button -> toggleShader()).dimensions(this.width / 2 - 100, this.height / 6 +  64, 200, 20).build();
        this.addDrawableChild(toggleShader);
        cycleShader = CyclingButtonWidget.builder(ShaderList::getName).values(ShaderList.values()).initially(ShaderList.cur).build(this.width / 2 - 100, this.height / 6 + 40, 200, 20, Text.of(""), (cyclingButtonWidget, shader) -> changeShader(true));
        cycleShader.setMessage(ShaderList.cur.getName());
        this.addDrawableChild(cycleShader);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            assert this.client != null;
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
    }

    private void changeShader(boolean d) {
        ShaderUtils.load(d);
        refreshText();
    }

    public void toggleShader() {
        ShaderUtils.enabled ^= true;
        if(ShaderUtils.shader != null) {
            assert this.client != null;
            ShaderUtils.shader.setupDimensions(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
        }
        refreshText();
    }

    private void refreshText() {
        cycleShader.setMessage(ShaderList.cur.getName());
        toggleShader.setMessage(Text.translatable("fooxy.sss.button." + ShaderUtils.enabled));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        assert this.client != null;
        if(!(this.client.getCameraEntity() instanceof PlayerEntity))
            this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }
}
