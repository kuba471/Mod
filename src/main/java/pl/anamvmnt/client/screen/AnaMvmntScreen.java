package pl.anamvmnt.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import pl.anamvmnt.client.AnaMvmntClient;
import pl.anamvmnt.client.config.ConfigManager;

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

    private static final int[] HAT_COLORS = {
            0xFFFF5555,
            0xFFFFAA00,
            0xFF55FF55,
            0xFF55AAFF,
            0xFFFFFFFF
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

        this.addDrawableChild(ButtonWidget.builder(Text.literal("HUD"), btn -> this.client.setScreen(new AnaMvmntScreen(Tab.HUD)))
                .dimensions(centerX - 5, baseY, 60, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("ESP"), btn -> this.client.setScreen(new AnaMvmntScreen(Tab.ESP)))
                .dimensions(centerX + 60, baseY, 60, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("ANIM"), btn -> this.client.setScreen(new AnaMvmntScreen(Tab.ANIMATION)))
                .dimensions(centerX + 125, baseY, 70, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("EXTRA"), btn -> this.client.setScreen(new AnaMvmntScreen(Tab.EXTRA)))
                .dimensions(centerX + 200, baseY, 70, 20).build());

        int y = this.height / 2 - 50;

        switch (activeTab) {
            case HUD -> initHudTab(centerX, y);
            case ESP -> initEspTab(centerX, y);
            case ANIMATION -> initAnimationTab(centerX, y);
            case EXTRA -> initExtraTab(centerX, y);
        }
    }

    private void initHudTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 240,
                () -> "Inventory HUD: " + onOff(AnaMvmntClient.SETTINGS.inventoryHudEnabled),
                () -> AnaMvmntClient.SETTINGS.inventoryHudEnabled = !AnaMvmntClient.SETTINGS.inventoryHudEnabled));

        this.addDrawableChild(toggleButton(centerX, y + 24, 240,
                () -> "Branding Left Top: " + onOff(AnaMvmntClient.SETTINGS.showBranding),
                () -> AnaMvmntClient.SETTINGS.showBranding = !AnaMvmntClient.SETTINGS.showBranding));

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
            ConfigManager.save(AnaMvmntClient.SETTINGS);
            this.client.setScreen(new AnaMvmntScreen(Tab.HUD));
        }).dimensions(centerX - 120, y + 120, 240, 20).build());
    }

    private void initEspTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 240,
                () -> "ESP: " + onOff(AnaMvmntClient.SETTINGS.espEnabled),
                () -> AnaMvmntClient.SETTINGS.espEnabled = !AnaMvmntClient.SETTINGS.espEnabled));

        this.addDrawableChild(toggleButton(centerX, y + 24, 240,
                () -> "ESP Through Walls: " + onOff(AnaMvmntClient.SETTINGS.espThroughWalls),
                () -> AnaMvmntClient.SETTINGS.espThroughWalls = !AnaMvmntClient.SETTINGS.espThroughWalls));

        this.addDrawableChild(toggleButton(centerX, y + 48, 240,
                () -> "Tracers: " + onOff(AnaMvmntClient.SETTINGS.tracersEnabled),
                () -> AnaMvmntClient.SETTINGS.tracersEnabled = !AnaMvmntClient.SETTINGS.tracersEnabled));

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Change ESP Color"), btn -> {
            int index = colorIndex(ESP_COLORS, AnaMvmntClient.SETTINGS.espColor);
            AnaMvmntClient.SETTINGS.espColor = ESP_COLORS[(index + 1) % ESP_COLORS.length];
        }).dimensions(centerX - 120, y + 72, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Hitbox -"), btn ->
                AnaMvmntClient.SETTINGS.hitboxScale = Math.max(0.30f, AnaMvmntClient.SETTINGS.hitboxScale - 0.05f))
                .dimensions(centerX - 120, y + 96, 115, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Hitbox +"), btn ->
                AnaMvmntClient.SETTINGS.hitboxScale = Math.min(2.50f, AnaMvmntClient.SETTINGS.hitboxScale + 0.05f))
                .dimensions(centerX + 5, y + 96, 115, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Chinese Hat: " + onOff(AnaMvmntClient.SETTINGS.chineseHatEnabled)), btn -> {
            AnaMvmntClient.SETTINGS.chineseHatEnabled = !AnaMvmntClient.SETTINGS.chineseHatEnabled;
            btn.setMessage(Text.literal("Chinese Hat: " + onOff(AnaMvmntClient.SETTINGS.chineseHatEnabled)));
        }).dimensions(centerX - 120, y + 120, 240, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Change Hat Color"), btn -> {
            int index = colorIndex(HAT_COLORS, AnaMvmntClient.SETTINGS.hatColor);
            AnaMvmntClient.SETTINGS.hatColor = HAT_COLORS[(index + 1) % HAT_COLORS.length];
        }).dimensions(centerX - 120, y + 144, 240, 20).build());
    }

    private void initAnimationTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 240,
                () -> "Freeze Legs: " + onOff(AnaMvmntClient.SETTINGS.freezeLegAnimation),
                () -> AnaMvmntClient.SETTINGS.freezeLegAnimation = !AnaMvmntClient.SETTINGS.freezeLegAnimation));

        this.addDrawableChild(toggleButton(centerX, y + 24, 240,
                () -> "Sword Swing Down: " + onOff(AnaMvmntClient.SETTINGS.swordSwingDownStyle),
                () -> AnaMvmntClient.SETTINGS.swordSwingDownStyle = !AnaMvmntClient.SETTINGS.swordSwingDownStyle));
    }

    private void initExtraTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 240,
                () -> "Armor HUD: " + onOff(AnaMvmntClient.SETTINGS.armorHudEnabled),
                () -> AnaMvmntClient.SETTINGS.armorHudEnabled = !AnaMvmntClient.SETTINGS.armorHudEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 24, 240,
                () -> "Coords HUD: " + onOff(AnaMvmntClient.SETTINGS.coordsHudEnabled),
                () -> AnaMvmntClient.SETTINGS.coordsHudEnabled = !AnaMvmntClient.SETTINGS.coordsHudEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 48, 240,
                () -> "FPS HUD: " + onOff(AnaMvmntClient.SETTINGS.fpsHudEnabled),
                () -> AnaMvmntClient.SETTINGS.fpsHudEnabled = !AnaMvmntClient.SETTINGS.fpsHudEnabled));

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Coords Left"), btn -> AnaMvmntClient.SETTINGS.coordsHudX -= 5)
                .dimensions(centerX - 120, y + 72, 115, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Coords Right"), btn -> AnaMvmntClient.SETTINGS.coordsHudX += 5)
                .dimensions(centerX + 5, y + 72, 115, 20).build());
    }

    private ButtonWidget toggleButton(int centerX, int y, int width, LabelSupplier label, ToggleAction action) {
        return ButtonWidget.builder(Text.literal(label.get()), btn -> {
            action.toggle();
            btn.setMessage(Text.literal(label.get()));
        }).dimensions(centerX - width / 2, y, width, 20).build();
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
    public void close() {
        ConfigManager.save(AnaMvmntClient.SETTINGS);
        super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.fill(this.width / 2 - 160, this.height / 2 - 106, this.width / 2 + 280, this.height / 2 + 120, 0xD0101010);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("AnaMvmnt - Smooth GUI"), this.width / 2 + 55, this.height / 2 - 84, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Active tab: " + activeTab.name()), this.width / 2 - 130, this.height / 2 - 68, 0xA0A0FF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Hitbox scale: " + String.format("%.2f", AnaMvmntClient.SETTINGS.hitboxScale)), this.width / 2 - 130, this.height / 2 - 54, 0x9CD5FF);
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

    private interface LabelSupplier {
        String get();
    }

    private interface ToggleAction {
        void toggle();
    }

    private enum Tab {
        HUD,
        ESP,
        ANIMATION,
        EXTRA
    }
}
