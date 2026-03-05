package pl.bettermvmnt.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import pl.bettermvmnt.client.BetterMvmntClient;
import pl.bettermvmnt.client.config.ConfigManager;

public final class BetterMvmntScreen extends Screen {
    private final Tab activeTab;
    private final int plusPage;

    public BetterMvmntScreen() {
        this(Tab.HUD, 0);
    }

    private BetterMvmntScreen(Tab tab, int plusPage) {
        super(Text.literal("BetterMvmnt"));
        this.activeTab = tab;
        this.plusPage = plusPage;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int baseY = this.height / 2 - 108;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("BetterMvmnt"), btn -> { })
                .dimensions(centerX - 170, baseY, 160, 20).build());

        this.addDrawableChild(tabBtn(centerX - 5, baseY, "HUD", Tab.HUD));
        this.addDrawableChild(tabBtn(centerX + 60, baseY, "ESP", Tab.ESP));
        this.addDrawableChild(tabBtn(centerX + 125, baseY, "ANIM", Tab.ANIMATION));
        this.addDrawableChild(tabBtn(centerX + 200, baseY, "EXTRA", Tab.EXTRA));
        this.addDrawableChild(tabBtn(centerX + 285, baseY, "PLUS", Tab.PLUS));

        int y = this.height / 2 - 62;

        switch (activeTab) {
            case HUD -> initHudTab(centerX, y);
            case ESP -> initEspTab(centerX, y);
            case ANIMATION -> initAnimationTab(centerX, y);
            case EXTRA -> initExtraTab(centerX, y);
            case PLUS -> initPlusTab(centerX, y);
        }
    }

    private ButtonWidget tabBtn(int x, int y, String name, Tab tab) {
        return ButtonWidget.builder(Text.literal(name), btn -> this.client.setScreen(new BetterMvmntScreen(tab, 0)))
                .dimensions(x, y, 60, 20).build();
    }

    private void initHudTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 260,
                () -> "Inventory HUD: " + onOff(BetterMvmntClient.SETTINGS.inventoryHudEnabled),
                () -> BetterMvmntClient.SETTINGS.inventoryHudEnabled = !BetterMvmntClient.SETTINGS.inventoryHudEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 24, 260,
                () -> "Branding: " + onOff(BetterMvmntClient.SETTINGS.showBranding),
                () -> BetterMvmntClient.SETTINGS.showBranding = !BetterMvmntClient.SETTINGS.showBranding));
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Change HUD (Drag & Drop)"), btn ->
                        this.client.setScreen(new HudEditorScreen()))
                .dimensions(centerX - 130, y + 48, 260, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("HUD Color R+"), btn ->
                        BetterMvmntClient.SETTINGS.inventoryHudColor = shiftColor(BetterMvmntClient.SETTINGS.inventoryHudColor, 16, 10))
                .dimensions(centerX - 130, y + 72, 84, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("G+"), btn ->
                        BetterMvmntClient.SETTINGS.inventoryHudColor = shiftColor(BetterMvmntClient.SETTINGS.inventoryHudColor, 8, 10))
                .dimensions(centerX - 42, y + 72, 84, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("B+"), btn ->
                        BetterMvmntClient.SETTINGS.inventoryHudColor = shiftColor(BetterMvmntClient.SETTINGS.inventoryHudColor, 0, 10))
                .dimensions(centerX + 46, y + 72, 84, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Safety Reset (disable all)"), btn -> {
            BetterMvmntClient.SETTINGS.safetyReset();
            ConfigManager.save(BetterMvmntClient.SETTINGS);
            this.client.setScreen(new BetterMvmntScreen(Tab.HUD, 0));
        }).dimensions(centerX - 130, y + 96, 260, 20).build());
    }

    private void initEspTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 260,
                () -> "ESP: " + onOff(BetterMvmntClient.SETTINGS.espEnabled),
                () -> BetterMvmntClient.SETTINGS.espEnabled = !BetterMvmntClient.SETTINGS.espEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 24, 260,
                () -> "ESP Through Walls: " + onOff(BetterMvmntClient.SETTINGS.espThroughWalls),
                () -> BetterMvmntClient.SETTINGS.espThroughWalls = !BetterMvmntClient.SETTINGS.espThroughWalls));
        this.addDrawableChild(toggleButton(centerX, y + 48, 260,
                () -> "Tracers: " + onOff(BetterMvmntClient.SETTINGS.tracersEnabled),
                () -> BetterMvmntClient.SETTINGS.tracersEnabled = !BetterMvmntClient.SETTINGS.tracersEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 72, 260,
                () -> "NameTags: " + onOff(BetterMvmntClient.SETTINGS.nametagsEnabled),
                () -> BetterMvmntClient.SETTINGS.nametagsEnabled = !BetterMvmntClient.SETTINGS.nametagsEnabled));

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Hitbox -"), btn ->
                        BetterMvmntClient.SETTINGS.hitboxScale = Math.max(0.30f, BetterMvmntClient.SETTINGS.hitboxScale - 0.05f))
                .dimensions(centerX - 130, y + 96, 128, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Hitbox +"), btn ->
                        BetterMvmntClient.SETTINGS.hitboxScale = Math.min(3.00f, BetterMvmntClient.SETTINGS.hitboxScale + 0.05f))
                .dimensions(centerX + 2, y + 96, 128, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("ESP R+/G+/B+"), btn -> {
            BetterMvmntClient.SETTINGS.espColor = shiftColor(BetterMvmntClient.SETTINGS.espColor, 16, 12);
            BetterMvmntClient.SETTINGS.espColor = shiftColor(BetterMvmntClient.SETTINGS.espColor, 8, 8);
            BetterMvmntClient.SETTINGS.espColor = shiftColor(BetterMvmntClient.SETTINGS.espColor, 0, 6);
        }).dimensions(centerX - 130, y + 120, 260, 20).build());

        this.addDrawableChild(toggleButton(centerX, y + 144, 260,
                () -> "Chinese Hat: " + onOff(BetterMvmntClient.SETTINGS.chineseHatEnabled),
                () -> BetterMvmntClient.SETTINGS.chineseHatEnabled = !BetterMvmntClient.SETTINGS.chineseHatEnabled));
    }

    private void initAnimationTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 260,
                () -> "Freeze Legs: " + onOff(BetterMvmntClient.SETTINGS.freezeLegAnimation),
                () -> BetterMvmntClient.SETTINGS.freezeLegAnimation = !BetterMvmntClient.SETTINGS.freezeLegAnimation));
        this.addDrawableChild(toggleButton(centerX, y + 24, 260,
                () -> "Sword Swing Down: " + onOff(BetterMvmntClient.SETTINGS.swordSwingDownStyle),
                () -> BetterMvmntClient.SETTINGS.swordSwingDownStyle = !BetterMvmntClient.SETTINGS.swordSwingDownStyle));
        this.addDrawableChild(toggleButton(centerX, y + 48, 260,
                () -> "Damage Particles: " + onOff(BetterMvmntClient.SETTINGS.damageParticles),
                () -> BetterMvmntClient.SETTINGS.damageParticles = !BetterMvmntClient.SETTINGS.damageParticles));
        this.addDrawableChild(toggleButton(centerX, y + 72, 260,
                () -> "Crosshair Mod: " + onOff(BetterMvmntClient.SETTINGS.crosshair),
                () -> BetterMvmntClient.SETTINGS.crosshair = !BetterMvmntClient.SETTINGS.crosshair));
    }

    private void initExtraTab(int centerX, int y) {
        this.addDrawableChild(toggleButton(centerX, y, 260,
                () -> "Armor HUD: " + onOff(BetterMvmntClient.SETTINGS.armorHudEnabled),
                () -> BetterMvmntClient.SETTINGS.armorHudEnabled = !BetterMvmntClient.SETTINGS.armorHudEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 24, 260,
                () -> "Coords HUD: " + onOff(BetterMvmntClient.SETTINGS.coordsHudEnabled),
                () -> BetterMvmntClient.SETTINGS.coordsHudEnabled = !BetterMvmntClient.SETTINGS.coordsHudEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 48, 260,
                () -> "FPS HUD: " + onOff(BetterMvmntClient.SETTINGS.fpsHudEnabled),
                () -> BetterMvmntClient.SETTINGS.fpsHudEnabled = !BetterMvmntClient.SETTINGS.fpsHudEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 72, 260,
                () -> "Freecam: " + onOff(BetterMvmntClient.SETTINGS.freecamEnabled),
                () -> BetterMvmntClient.SETTINGS.freecamEnabled = !BetterMvmntClient.SETTINGS.freecamEnabled));
        this.addDrawableChild(toggleButton(centerX, y + 96, 260,
                () -> "No Push: " + onOff(BetterMvmntClient.SETTINGS.noPush),
                () -> BetterMvmntClient.SETTINGS.noPush = !BetterMvmntClient.SETTINGS.noPush));
        this.addDrawableChild(toggleButton(centerX, y + 120, 260,
                () -> "No Block Particles: " + onOff(BetterMvmntClient.SETTINGS.noBlockParticles),
                () -> BetterMvmntClient.SETTINGS.noBlockParticles = !BetterMvmntClient.SETTINGS.noBlockParticles));
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Open HUD Editor"), btn ->
                        this.client.setScreen(new HudEditorScreen()))
                .dimensions(centerX - 130, y + 144, 260, 20).build());
    }

    private void initPlusTab(int centerX, int y) {
        if (plusPage == 0) {
            addPlusToggle(centerX, y, "Sprint", () -> BetterMvmntClient.SETTINGS.sprint = !BetterMvmntClient.SETTINGS.sprint, () -> BetterMvmntClient.SETTINGS.sprint);
            addPlusToggle(centerX, y + 24, "AutoJump", () -> BetterMvmntClient.SETTINGS.autoJump = !BetterMvmntClient.SETTINGS.autoJump, () -> BetterMvmntClient.SETTINGS.autoJump);
            addPlusToggle(centerX, y + 48, "Speed", () -> BetterMvmntClient.SETTINGS.speed = !BetterMvmntClient.SETTINGS.speed, () -> BetterMvmntClient.SETTINGS.speed);
            addPlusToggle(centerX, y + 72, "LongJump", () -> BetterMvmntClient.SETTINGS.longJump = !BetterMvmntClient.SETTINGS.longJump, () -> BetterMvmntClient.SETTINGS.longJump);
            addPlusToggle(centerX, y + 96, "HighJump", () -> BetterMvmntClient.SETTINGS.highJump = !BetterMvmntClient.SETTINGS.highJump, () -> BetterMvmntClient.SETTINGS.highJump);
            addPlusToggle(centerX, y + 120, "LowGravity", () -> BetterMvmntClient.SETTINGS.lowGravity = !BetterMvmntClient.SETTINGS.lowGravity, () -> BetterMvmntClient.SETTINGS.lowGravity);
            addPlusToggle(centerX, y + 144, "Step", () -> BetterMvmntClient.SETTINGS.step = !BetterMvmntClient.SETTINGS.step, () -> BetterMvmntClient.SETTINGS.step);
            addPlusToggle(centerX, y + 168, "SafeWalk", () -> BetterMvmntClient.SETTINGS.safeWalk = !BetterMvmntClient.SETTINGS.safeWalk, () -> BetterMvmntClient.SETTINGS.safeWalk);
        } else if (plusPage == 1) {
            addPlusToggle(centerX, y, "NoSlow", () -> BetterMvmntClient.SETTINGS.noSlow = !BetterMvmntClient.SETTINGS.noSlow, () -> BetterMvmntClient.SETTINGS.noSlow);
            addPlusToggle(centerX, y + 24, "Velocity", () -> BetterMvmntClient.SETTINGS.velocity = !BetterMvmntClient.SETTINGS.velocity, () -> BetterMvmntClient.SETTINGS.velocity);
            addPlusToggle(centerX, y + 48, "AntiKnockback", () -> BetterMvmntClient.SETTINGS.antiKnockback = !BetterMvmntClient.SETTINGS.antiKnockback, () -> BetterMvmntClient.SETTINGS.antiKnockback);
            addPlusToggle(centerX, y + 72, "FastPlace", () -> BetterMvmntClient.SETTINGS.fastPlace = !BetterMvmntClient.SETTINGS.fastPlace, () -> BetterMvmntClient.SETTINGS.fastPlace);
            addPlusToggle(centerX, y + 96, "FastBreak", () -> BetterMvmntClient.SETTINGS.fastBreak = !BetterMvmntClient.SETTINGS.fastBreak, () -> BetterMvmntClient.SETTINGS.fastBreak);
            addPlusToggle(centerX, y + 120, "Reach", () -> BetterMvmntClient.SETTINGS.reach = !BetterMvmntClient.SETTINGS.reach, () -> BetterMvmntClient.SETTINGS.reach);
            addPlusToggle(centerX, y + 144, "TriggerBot", () -> BetterMvmntClient.SETTINGS.triggerBot = !BetterMvmntClient.SETTINGS.triggerBot, () -> BetterMvmntClient.SETTINGS.triggerBot);
            addPlusToggle(centerX, y + 168, "AimAssist", () -> BetterMvmntClient.SETTINGS.aimAssist = !BetterMvmntClient.SETTINGS.aimAssist, () -> BetterMvmntClient.SETTINGS.aimAssist);
        } else if (plusPage == 2) {
            addPlusToggle(centerX, y, "AutoTotem", () -> BetterMvmntClient.SETTINGS.autoTotem = !BetterMvmntClient.SETTINGS.autoTotem, () -> BetterMvmntClient.SETTINGS.autoTotem);
            addPlusToggle(centerX, y + 24, "AutoArmor", () -> BetterMvmntClient.SETTINGS.autoArmor = !BetterMvmntClient.SETTINGS.autoArmor, () -> BetterMvmntClient.SETTINGS.autoArmor);
            addPlusToggle(centerX, y + 48, "ChestStealer", () -> BetterMvmntClient.SETTINGS.chestStealer = !BetterMvmntClient.SETTINGS.chestStealer, () -> BetterMvmntClient.SETTINGS.chestStealer);
            addPlusToggle(centerX, y + 72, "Scaffold", () -> BetterMvmntClient.SETTINGS.scaffold = !BetterMvmntClient.SETTINGS.scaffold, () -> BetterMvmntClient.SETTINGS.scaffold);
            addPlusToggle(centerX, y + 96, "Blink", () -> BetterMvmntClient.SETTINGS.blink = !BetterMvmntClient.SETTINGS.blink, () -> BetterMvmntClient.SETTINGS.blink);
            addPlusToggle(centerX, y + 120, "Timer", () -> BetterMvmntClient.SETTINGS.timer = !BetterMvmntClient.SETTINGS.timer, () -> BetterMvmntClient.SETTINGS.timer);
            addPlusToggle(centerX, y + 144, "FullBright", () -> BetterMvmntClient.SETTINGS.fullBright = !BetterMvmntClient.SETTINGS.fullBright, () -> BetterMvmntClient.SETTINGS.fullBright);
            addPlusToggle(centerX, y + 168, "Xray", () -> BetterMvmntClient.SETTINGS.xray = !BetterMvmntClient.SETTINGS.xray, () -> BetterMvmntClient.SETTINGS.xray);
        } else {
            addPlusToggle(centerX, y, "ItemESP", () -> BetterMvmntClient.SETTINGS.itemEsp = !BetterMvmntClient.SETTINGS.itemEsp, () -> BetterMvmntClient.SETTINGS.itemEsp);
            addPlusToggle(centerX, y + 24, "PlayerRadar", () -> BetterMvmntClient.SETTINGS.playerRadar = !BetterMvmntClient.SETTINGS.playerRadar, () -> BetterMvmntClient.SETTINGS.playerRadar);
            addPlusToggle(centerX, y + 48, "Weather", () -> BetterMvmntClient.SETTINGS.weather = !BetterMvmntClient.SETTINGS.weather, () -> BetterMvmntClient.SETTINGS.weather);
            addPlusToggle(centerX, y + 72, "TimeChanger", () -> BetterMvmntClient.SETTINGS.timeChanger = !BetterMvmntClient.SETTINGS.timeChanger, () -> BetterMvmntClient.SETTINGS.timeChanger);
            addPlusToggle(centerX, y + 96, "NoPush", () -> BetterMvmntClient.SETTINGS.noPush = !BetterMvmntClient.SETTINGS.noPush, () -> BetterMvmntClient.SETTINGS.noPush);
            addPlusToggle(centerX, y + 120, "NoBlockParticles", () -> BetterMvmntClient.SETTINGS.noBlockParticles = !BetterMvmntClient.SETTINGS.noBlockParticles, () -> BetterMvmntClient.SETTINGS.noBlockParticles);
            addPlusToggle(centerX, y + 144, "Freecam", () -> BetterMvmntClient.SETTINGS.freecamEnabled = !BetterMvmntClient.SETTINGS.freecamEnabled, () -> BetterMvmntClient.SETTINGS.freecamEnabled);
            addPlusToggle(centerX, y + 168, "DamageParticles", () -> BetterMvmntClient.SETTINGS.damageParticles = !BetterMvmntClient.SETTINGS.damageParticles, () -> BetterMvmntClient.SETTINGS.damageParticles);
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), btn -> this.client.setScreen(new BetterMvmntScreen(Tab.PLUS, Math.max(0, plusPage - 1))))
                .dimensions(centerX - 130, y + 192, 60, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Page " + (plusPage + 1) + "/4"), btn -> { })
                .dimensions(centerX - 66, y + 192, 132, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal(">"), btn -> this.client.setScreen(new BetterMvmntScreen(Tab.PLUS, Math.min(3, plusPage + 1))))
                .dimensions(centerX + 70, y + 192, 60, 20).build());
    }

    private void addPlusToggle(int centerX, int y, String name, Runnable action, BoolReader reader) {
        this.addDrawableChild(ButtonWidget.builder(Text.literal(name + ": " + onOff(reader.get())), btn -> {
            action.run();
            btn.setMessage(Text.literal(name + ": " + onOff(reader.get())));
        }).dimensions(centerX - 130, y, 260, 20).build());
    }

    private static int shiftColor(int color, int channelShift, int amount) {
        int a = (color >> 24) & 255;
        int r = (color >> 16) & 255;
        int g = (color >> 8) & 255;
        int b = color & 255;
        if (channelShift == 16) {
            r = Math.min(255, r + amount);
        } else if (channelShift == 8) {
            g = Math.min(255, g + amount);
        } else {
            b = Math.min(255, b + amount);
        }
        return (a << 24) | (r << 16) | (g << 8) | b;
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
        ConfigManager.save(BetterMvmntClient.SETTINGS);
        super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.fill(this.width / 2 - 180, this.height / 2 - 122, this.width / 2 + 360, this.height / 2 + 170, 0xD00E1018);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("BetterMvmnt - Ultra Smooth GUI"), this.width / 2 + 80, this.height / 2 - 100, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Active tab: " + activeTab.name()), this.width / 2 - 150, this.height / 2 - 84, 0xA0A0FF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Hitbox scale: " + String.format("%.2f", BetterMvmntClient.SETTINGS.hitboxScale)), this.width / 2 - 150, this.height / 2 - 72, 0x9CD5FF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Hotkeys: G Tracers | V ESP | F6 Freecam | H HUD Editor"), this.width / 2 - 150, this.height / 2 - 60, 0xC0C0C0);
        context.drawTextWithShadow(this.textRenderer, Text.literal("BetterMvmnt"), 8, 8, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("discord.gg/piracik"), 8, 18, 0x9A9A9A);
        super.render(context, mouseX, mouseY, delta);
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

    private interface BoolReader {
        boolean get();
    }

    private enum Tab {
        HUD,
        ESP,
        ANIMATION,
        EXTRA,
        PLUS
    }
}
