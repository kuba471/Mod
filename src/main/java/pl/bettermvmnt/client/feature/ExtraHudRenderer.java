package pl.bettermvmnt.client.feature;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
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
        if (client.player == null || client.world == null) {
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
            context.drawTextWithShadow(
                    client.textRenderer,
                    Text.literal("FPS: " + client.getCurrentFps()),
                    BetterMvmntClient.SETTINGS.fpsHudX,
                    BetterMvmntClient.SETTINGS.fpsHudY,
                    0x55FF55
            );
        }

        if (BetterMvmntClient.SETTINGS.nametagsEnabled) {
            int y = 8;
            context.drawTextWithShadow(client.textRenderer, Text.literal("Nametags"), 8, y, 0xFFC6FF);
            y += 10;
            for (PlayerEntity p : client.world.getPlayers()) {
                if (p == client.player) {
                    continue;
                }
                int dist = (int) client.player.distanceTo(p);
                context.drawTextWithShadow(client.textRenderer, Text.literal(p.getName().getString() + " [" + dist + "m]"), 8, y, 0xFFFFFF);
                y += 10;
                if (y > 100) {
                    break;
                }
            }
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
