package pl.bettermvmnt.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import pl.bettermvmnt.client.BetterMvmntClient;
import pl.bettermvmnt.client.config.ConfigManager;

public final class HudEditorScreen extends Screen {
    private DragTarget dragging;
    private int dragOffsetX;
    private int dragOffsetY;

    public HudEditorScreen() {
        super(Text.literal("Change HUD"));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        DragTarget target = hitTarget((int) mouseX, (int) mouseY);
        if (target != null) {
            dragging = target;
            dragOffsetX = (int) mouseX - getX(target);
            dragOffsetY = (int) mouseY - getY(target);
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && dragging != null) {
            setPosition(dragging, (int) mouseX - dragOffsetX, (int) mouseY - dragOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = null;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 || keyCode == 340 || keyCode == 344) {
            ConfigManager.save(BetterMvmntClient.SETTINGS);
            if (this.client != null) {
                this.client.setScreen(new BetterMvmntScreen());
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.fill(6, 6, 250, 62, 0xB0101010);
        context.drawTextWithShadow(this.textRenderer, Text.literal("Change HUD (drag & drop)"), 12, 12, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, Text.literal("LPM: drag box | ESC/SHIFT: save & back"), 12, 24, 0xA8D5FF);

        drawBox(context, DragTarget.INVENTORY, BetterMvmntClient.SETTINGS.inventoryHudX, BetterMvmntClient.SETTINGS.inventoryHudY, 9 * 18, 3 * 18, 0x66FFFFFF);
        drawBox(context, DragTarget.ARMOR, BetterMvmntClient.SETTINGS.armorHudX, BetterMvmntClient.SETTINGS.armorHudY, 4 * 18, 18, 0x66FFCC66);
        drawBox(context, DragTarget.COORDS, BetterMvmntClient.SETTINGS.coordsHudX, BetterMvmntClient.SETTINGS.coordsHudY, 150, 12, 0x6688CCFF);
        drawBox(context, DragTarget.FPS, BetterMvmntClient.SETTINGS.fpsHudX, BetterMvmntClient.SETTINGS.fpsHudY, 80, 12, 0x6688FF88);

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawBox(DrawContext context, DragTarget target, int x, int y, int w, int h, int color) {
        int border = dragging == target ? 0xFFFFFFFF : 0xFF303030;
        context.fill(x, y, x + w, y + h, color);
        context.drawBorder(x, y, w, h, border);
        context.drawTextWithShadow(this.textRenderer, Text.literal(target.label), x + 3, y + 2, 0xFFFFFF);
    }

    private DragTarget hitTarget(int x, int y) {
        for (DragTarget target : DragTarget.values()) {
            int tx = getX(target);
            int ty = getY(target);
            int tw = getW(target);
            int th = getH(target);
            if (x >= tx && x <= tx + tw && y >= ty && y <= ty + th) {
                return target;
            }
        }
        return null;
    }

    private int getX(DragTarget target) {
        return switch (target) {
            case INVENTORY -> BetterMvmntClient.SETTINGS.inventoryHudX;
            case ARMOR -> BetterMvmntClient.SETTINGS.armorHudX;
            case COORDS -> BetterMvmntClient.SETTINGS.coordsHudX;
            case FPS -> BetterMvmntClient.SETTINGS.fpsHudX;
        };
    }

    private int getY(DragTarget target) {
        return switch (target) {
            case INVENTORY -> BetterMvmntClient.SETTINGS.inventoryHudY;
            case ARMOR -> BetterMvmntClient.SETTINGS.armorHudY;
            case COORDS -> BetterMvmntClient.SETTINGS.coordsHudY;
            case FPS -> BetterMvmntClient.SETTINGS.fpsHudY;
        };
    }

    private int getW(DragTarget target) {
        return switch (target) {
            case INVENTORY -> 9 * 18;
            case ARMOR -> 4 * 18;
            case COORDS -> 150;
            case FPS -> 80;
        };
    }

    private int getH(DragTarget target) {
        return switch (target) {
            case INVENTORY -> 3 * 18;
            case ARMOR -> 18;
            case COORDS, FPS -> 12;
        };
    }

    private void setPosition(DragTarget target, int x, int y) {
        x = Math.max(0, Math.min(this.width - getW(target), x));
        y = Math.max(0, Math.min(this.height - getH(target), y));

        switch (target) {
            case INVENTORY -> {
                BetterMvmntClient.SETTINGS.inventoryHudX = x;
                BetterMvmntClient.SETTINGS.inventoryHudY = y;
            }
            case ARMOR -> {
                BetterMvmntClient.SETTINGS.armorHudX = x;
                BetterMvmntClient.SETTINGS.armorHudY = y;
            }
            case COORDS -> {
                BetterMvmntClient.SETTINGS.coordsHudX = x;
                BetterMvmntClient.SETTINGS.coordsHudY = y;
            }
            case FPS -> {
                BetterMvmntClient.SETTINGS.fpsHudX = x;
                BetterMvmntClient.SETTINGS.fpsHudY = y;
            }
        }
    }

    private enum DragTarget {
        INVENTORY("Inventory HUD"),
        ARMOR("Armor HUD"),
        COORDS("Coords HUD"),
        FPS("FPS HUD");

        private final String label;

        DragTarget(String label) {
            this.label = label;
        }
    }
}
