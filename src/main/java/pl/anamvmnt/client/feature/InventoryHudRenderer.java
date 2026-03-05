package pl.anamvmnt.client.feature;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import pl.anamvmnt.client.AnaMvmntClient;

public final class InventoryHudRenderer {
    private InventoryHudRenderer() {
    }

    public static void register() {
        HudRenderCallback.EVENT.register((context, tickCounter) -> render(context, tickCounter));
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }

        if (AnaMvmntClient.SETTINGS.showBranding) {
            context.drawTextWithShadow(client.textRenderer, Text.literal("AnaMvmnt"), 8, 8, 0xFFFFFF);
            context.drawTextWithShadow(client.textRenderer, Text.literal("discord.gg/piracik"), 8, 18, 0xA0A0A0);
        }

        if (!AnaMvmntClient.SETTINGS.inventoryHudEnabled) {
            return;
        }

        int x = AnaMvmntClient.SETTINGS.inventoryHudX;
        int y = AnaMvmntClient.SETTINGS.inventoryHudY;

        context.fill(x - 4, y - 4, x + 9 * 18 + 4, y + 3 * 18 + 4, AnaMvmntClient.SETTINGS.inventoryHudColor);

        PlayerInventory inv = client.player.getInventory();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slot = col + row * 9 + 9;
                ItemStack stack = inv.getStack(slot);
                int itemX = x + col * 18;
                int itemY = y + row * 18;
                context.drawItem(stack, itemX, itemY);
                context.drawStackOverlay(client.textRenderer, stack, itemX, itemY);
            }
        }
    }
}
