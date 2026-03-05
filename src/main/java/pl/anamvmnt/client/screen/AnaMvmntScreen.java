package pl.anamvmnt.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import pl.anamvmnt.client.AnaMvmntClient;

public final class AnaMvmntScreen extends Screen {
    private final Tab activeTab;

    private static final int[] ESP_COLORS = {
            0xFF00FFAA,
            0xFFFF6666,
            0xFF66AAFF,
            0xFFFFFF66,
            0xFFFFFFFF
    };

    private static final int[] HUD_BG_COLORS = {
            0xAA202020,
            0xAA0A0F1C,
            0xAA1E0A0A,
            0xAA113011,
            0xAA2A1E08
    };

    public AnaMvmntScreen() {
        this(Tab.HUD);
    }

    private AnaMvmntScreen(Tab tab) {
        super(Text.literal("AnaMvmnt"));
        this.activeTab = tab;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int baseY = this.height / 2 - 92;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("AnaMvmnt"), btn -> { })
                .dimensions(centerX - 140, baseY, 130, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Hud"), btn -> this.client.setScreen(new AnaMvmntScreen(Tab.HUD)))
                .dimensions(centerX - 5, baseY, 60, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("ESP"), btn -> this.client.setScreen(new AnaMvmntScreen(Tab.ESP)))
                .dimensions(centerX + 60, baseY, 60, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Animation"), btn -> this.client.setScreen(new AnaMvmntScreen(Tab.ANIMATION)))
                .dimensions(centerX + 125, baseY, 90, 20).build());

        int y = this.height / 2 - 50;

        switch (activeTab) {
            case HUD -> initHudTab(centerX, y);
            case ESP -> initEspTab(centerX, y);
            case ANIMATION -> initAnimationTab(centerX, y);
        }
    }

    private void initHudTab(int centerX, int y) {
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("Inventory HUD: " + onOff(AnaMvmntClient.SETTINGS.inventoryHudEnabled)),
                        btn -> {
                            AnaMvmntClient.SETTINGS.inventoryHudEnabled = !AnaMvmntClient.SETTINGS.inventoryHudEnabled;
                            btn.setMessage(Text.literal("Inventory HUD: " + onOff(AnaMvmntClient.SETTINGS.inventoryHudEnabled)));
                        })
                .dimensions(centerX - 120, y, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("Branding Left Top: " + onOff(AnaMvmntClient.SETTINGS.showBranding)),
                        btn -> {
                            AnaMvmntClient.SETTINGS.showBranding = !AnaMvmntClient.SETTINGS.showBranding;
                            btn.setMessage(Text.literal("Branding Left Top: " + onOff(AnaMvmntClient.SETTINGS.showBranding)));
                        })
                .dimensions(centerX - 120, y + 24, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Move HUD Left"), btn -> AnaMvmntClient.SETTINGS.inventoryHudX -= 5)
                .dimensions(centerX - 120, y + 48, 115, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Move HUD Right"), btn -> AnaMvmntClient.SETTINGS.inventoryHudX += 5)
                .dimensions(centerX + 5, y + 48, 115, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Move HUD Up"), btn -> AnaMvmntClient.SETTINGS.inventoryHudY -= 5)
                .dimensions(centerX - 120, y + 72, 115, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Move HUD Down"), btn -> AnaMvmntClient.SETTINGS.inventoryHudY += 5)
                .dimensions(centerX + 5, y + 72, 115, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Change HUD Background Color"), btn -> {
            int index = colorIndex(HUD_BG_COLORS, AnaMvmntClient.SETTINGS.inventoryHudColor);
            AnaMvmntClient.SETTINGS.inventoryHudColor = HUD_BG_COLORS[(index + 1) % HUD_BG_COLORS.length];
        }).dimensions(centerX - 120, y + 96, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Safety Reset (disable all)"), btn -> {
            AnaMvmntClient.SETTINGS.safetyReset();
            this.client.setScreen(new AnaMvmntScreen(Tab.HUD));
        }).dimensions(centerX - 120, y + 120, 240, 20).build());
    }

    private void initEspTab(int centerX, int y) {
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("ESP: " + onOff(AnaMvmntClient.SETTINGS.espEnabled)),
                        btn -> {
                            AnaMvmntClient.SETTINGS.espEnabled = !AnaMvmntClient.SETTINGS.espEnabled;
                            btn.setMessage(Text.literal("ESP: " + onOff(AnaMvmntClient.SETTINGS.espEnabled)));
                        })
                .dimensions(centerX - 120, y, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Change ESP Color"), btn -> {
            int index = colorIndex(ESP_COLORS, AnaMvmntClient.SETTINGS.espColor);
            AnaMvmntClient.SETTINGS.espColor = ESP_COLORS[(index + 1) % ESP_COLORS.length];
        }).dimensions(centerX - 120, y + 24, 240, 20).build());
    }

    private void initAnimationTab(int centerX, int y) {
        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("Freeze Legs: " + onOff(AnaMvmntClient.SETTINGS.freezeLegAnimation)),
                        btn -> {
                            AnaMvmntClient.SETTINGS.freezeLegAnimation = !AnaMvmntClient.SETTINGS.freezeLegAnimation;
                            btn.setMessage(Text.literal("Freeze Legs: " + onOff(AnaMvmntClient.SETTINGS.freezeLegAnimation)));
                        })
                .dimensions(centerX - 120, y, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("Sword Swing Down: " + onOff(AnaMvmntClient.SETTINGS.swordSwingDownStyle)),
                        btn -> {
                            AnaMvmntClient.SETTINGS.swordSwingDownStyle = !AnaMvmntClient.SETTINGS.swordSwingDownStyle;
                            btn.setMessage(Text.literal("Sword Swing Down: " + onOff(AnaMvmntClient.SETTINGS.swordSwingDownStyle)));
                        })
                .dimensions(centerX - 120, y + 24, 240, 20).build());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 340 || keyCode == 344) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.fill(this.width / 2 - 160, this.height / 2 - 106, this.width / 2 + 230, this.height / 2 + 120, 0xD0101010);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("AnaMvmnt - Smooth GUI"), this.width / 2 + 35, this.height / 2 - 84, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Active tab: " + activeTab.name()), this.width / 2 - 130, this.height / 2 - 68, 0xA0A0FF);

        context.drawTextWithShadow(this.textRenderer, Text.literal("AnaMvmnt"), 8, 8, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("discord.gg/piracik"), 8, 18, 0x9A9A9A);

        super.render(context, mouseX, mouseY, delta);
    }

    private static int colorIndex(int[] palette, int color) {
        for (int i = 0; i < palette.length; i++) {
            if (palette[i] == color) {
                return i;
            }
        }
        return 0;
    }

    private static String onOff(boolean value) {
        return value ? "ON" : "OFF";
    }

    private enum Tab {
        HUD,
        ESP,
        ANIMATION
    }
}
