package pl.bettermvmnt.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class BetterIntroScreen extends Screen {
    private int ticks;

    public BetterIntroScreen() {
        super(Text.literal("BetterMvmnt Intro"));
    }

    @Override
    public void tick() {
        ticks++;
        if (this.client != null && ticks > 45) {
            this.client.setScreen(new BetterMvmntScreen());
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        int cx = this.width / 2;
        int cy = this.height / 2;

        int barWidth = 220;
        int progress = Math.min(barWidth, (int) (barWidth * (ticks / 45.0f)));

        context.fill(cx - 140, cy - 70, cx + 140, cy + 62, 0xD0101010);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("BetterMvmnt"), cx, cy - 48, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("discord.gg/piracik"), cx, cy - 34, 0xAFAFAF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Loading visual modules..."), cx, cy - 8, 0x9BD2FF);

        context.fill(cx - barWidth / 2, cy + 18, cx + barWidth / 2, cy + 30, 0x50303030);
        context.fill(cx - barWidth / 2, cy + 18, cx - barWidth / 2 + progress, cy + 30, 0xFF4AA3FF);

        String pct = (int) Math.min(100, (ticks / 45.0f) * 100) + "%";
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal(pct), cx, cy + 36, 0xFFFFFF);

        super.render(context, mouseX, mouseY, delta);
    }
}
