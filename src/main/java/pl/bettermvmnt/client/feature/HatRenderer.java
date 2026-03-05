package pl.bettermvmnt.client.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import pl.bettermvmnt.client.BetterMvmntClient;

public final class HatRenderer {
    private HatRenderer() {
    }

    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(HatRenderer::render);
    }

    private static void render(WorldRenderContext context) {
        if (!BetterMvmntClient.SETTINGS.chineseHatEnabled) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || context.matrixStack() == null) {
            return;
        }

        Camera camera = context.camera();
        Vec3d camPos = camera.getPos();

        int color = BetterMvmntClient.SETTINGS.hatColor;
        float r = ((color >> 16) & 255) / 255.0f;
        float g = ((color >> 8) & 255) / 255.0f;
        float b = (color & 255) / 255.0f;
        float a = ((color >> 24) & 255) / 255.0f;

        VertexConsumerProvider.Immediate immediate = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer lines = immediate.getBuffer(RenderLayer.getLines());

        RenderSystem.disableDepthTest();
        for (PlayerEntity player : client.world.getPlayers()) {
            drawHat(context.matrixStack(), lines, player, camPos, r, g, b, a);
        }
        immediate.draw();
        RenderSystem.enableDepthTest();
    }

    private static void drawHat(MatrixStack matrices, VertexConsumer lines, PlayerEntity player, Vec3d camPos,
                                float r, float g, float b, float a) {
        Vec3d base = player.getPos().add(0.0, player.getHeight() + 0.12, 0.0).subtract(camPos);

        float radius = 0.55f;
        float topHeight = 0.32f;
        int segments = 24;

        float topX = (float) base.x;
        float topY = (float) (base.y + topHeight);
        float topZ = (float) base.z;

        var matrix = matrices.peek().getPositionMatrix();

        for (int i = 0; i < segments; i++) {
            double a0 = (Math.PI * 2.0 * i) / segments;
            double a1 = (Math.PI * 2.0 * (i + 1)) / segments;

            float x0 = (float) (base.x + Math.cos(a0) * radius);
            float z0 = (float) (base.z + Math.sin(a0) * radius);
            float x1 = (float) (base.x + Math.cos(a1) * radius);
            float z1 = (float) (base.z + Math.sin(a1) * radius);
            float y = (float) base.y;

            lines.vertex(matrix, x0, y, z0).color(r, g, b, a).normal(0.0f, 1.0f, 0.0f);
            lines.vertex(matrix, x1, y, z1).color(r, g, b, a).normal(0.0f, 1.0f, 0.0f);

            lines.vertex(matrix, x0, y, z0).color(r, g, b, a).normal(0.0f, 1.0f, 0.0f);
            lines.vertex(matrix, topX, topY, topZ).color(r, g, b, a).normal(0.0f, 1.0f, 0.0f);
        }
    }
}
