package pl.anamvmnt.client.feature;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import pl.anamvmnt.client.AnaMvmntClient;

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

        if (AnaMvmntClient.SETTINGS.armorHudEnabled) {
            renderArmorHud(context, client);
        }

        if (AnaMvmntClient.SETTINGS.coordsHudEnabled) {
            int x = AnaMvmntClient.SETTINGS.coordsHudX;
            int y = AnaMvmntClient.SETTINGS.coordsHudY;
            String coords = String.format("XYZ: %.1f / %.1f / %.1f", client.player.getX(), client.player.getY(), client.player.getZ());
            context.drawTextWithShadow(client.textRenderer, Text.literal(coords), x, y, 0xFFFFFF);
        }

        if (AnaMvmntClient.SETTINGS.fpsHudEnabled) {
            context.drawTextWithShadow(client.textRenderer, Text.literal("FPS: " + client.getCurrentFps()), 8, 30, 0x55FF55);
        }
    }

    private static void renderArmorHud(DrawContext context, MinecraftClient client) {
        int x = AnaMvmntClient.SETTINGS.armorHudX;
        int y = AnaMvmntClient.SETTINGS.armorHudY;

        int i = 0;
        for (ItemStack stack : client.player.getInventory().armor) {
            int itemX = x + i * 18;
            context.drawItem(stack, itemX, y);
            context.drawStackOverlay(client.textRenderer, stack, itemX, y);
            i++;
        }
    }
}
