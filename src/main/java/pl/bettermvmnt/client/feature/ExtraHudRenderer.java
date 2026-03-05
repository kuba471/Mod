package pl.bettermvmnt.client.feature;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import pl.bettermvmnt.client.BetterMvmntClient;

public final class ExtraHudRenderer {
    private ExtraHudRenderer() {
    }

    public static void register() {
        HudRenderCallback.EVENT.register(ExtraHudRenderer::render);
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }

        if (BetterMvmntClient.SETTINGS.armorHudEnabled) {
            renderArmorHud(context, client);
        }

        if (BetterMvmntClient.SETTINGS.coordsHudEnabled) {
            int x = BetterMvmntClient.SETTINGS.coordsHudX;
            int y = BetterMvmntClient.SETTINGS.coordsHudY;
            String coords = String.format("XYZ: %.1f / %.1f / %.1f", client.player.getX(), client.player.getY(), client.player.getZ());
            context.drawTextWithShadow(client.textRenderer, Text.literal(coords), x, y, 0xFFFFFF);
        }

        if (BetterMvmntClient.SETTINGS.fpsHudEnabled) {
            context.drawTextWithShadow(client.textRenderer, Text.literal("FPS: " + client.getCurrentFps()), 8, 30, 0x55FF55);
        }
    }

    private static void renderArmorHud(DrawContext context, MinecraftClient client) {
        int x = BetterMvmntClient.SETTINGS.armorHudX;
        int y = BetterMvmntClient.SETTINGS.armorHudY;

        int i = 0;
        for (ItemStack stack : client.player.getInventory().armor) {
            int itemX = x + i * 18;
            context.drawItem(stack, itemX, y);
            context.drawStackOverlay(client.textRenderer, stack, itemX, y);
            i++;
        }
    }
}
