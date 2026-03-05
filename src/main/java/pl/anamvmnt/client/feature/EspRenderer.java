package pl.anamvmnt.client.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import pl.anamvmnt.client.AnaMvmntClient;

public final class EspRenderer {
    private EspRenderer() {
    }

    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(EspRenderer::render);
    }

    private static void render(WorldRenderContext context) {
        if (!AnaMvmntClient.SETTINGS.espEnabled) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null || context.matrixStack() == null) {
            return;
        }

        MatrixStack matrices = context.matrixStack();
        Camera camera = context.camera();
        Vec3d camPos = camera.getPos();

        int color = AnaMvmntClient.SETTINGS.espColor;
        float r = ((color >> 16) & 255) / 255.0f;
        float g = ((color >> 8) & 255) / 255.0f;
        float b = (color & 255) / 255.0f;
        float a = ((color >> 24) & 255) / 255.0f;

        VertexConsumerProvider.Immediate immediate = client.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer lines = immediate.getBuffer(RenderLayer.getLines());

        if (AnaMvmntClient.SETTINGS.espThroughWalls) {
            RenderSystem.disableDepthTest();
        }

        for (Entity entity : client.world.getEntities()) {
            if (!(entity instanceof PlayerEntity) || entity == client.player) {
                continue;
            }

            Box base = entity.getBoundingBox().offset(-camPos.x, -camPos.y, -camPos.z).expand(0.02);
            Box scaled = scaleBoxFromCenter(base, AnaMvmntClient.SETTINGS.hitboxScale);
            VertexRendering.drawBox(matrices, lines, scaled, r, g, b, a);

            if (AnaMvmntClient.SETTINGS.tracersEnabled) {
                drawTracer(matrices, lines, camera, entity, camPos, r, g, b, a);
            }
        }

        immediate.draw();

        if (AnaMvmntClient.SETTINGS.espThroughWalls) {
            RenderSystem.enableDepthTest();
        }
    }

    private static Box scaleBoxFromCenter(Box box, float scale) {
        if (scale <= 0.0f) {
            return box;
        }
        double dx = (box.maxX - box.minX) * (scale - 1.0) / 2.0;
        double dy = (box.maxY - box.minY) * (scale - 1.0) / 2.0;
        double dz = (box.maxZ - box.minZ) * (scale - 1.0) / 2.0;
        return box.expand(dx, dy, dz);
    }

    private static void drawTracer(
            MatrixStack matrices,
            VertexConsumer lines,
            Camera camera,
            Entity entity,
            Vec3d camPos,
            float r,
            float g,
            float b,
            float a
    ) {
        Vec3d to = entity.getBoundingBox().getCenter();

        double startX = 0.0;
        double startY = camera.getFocusedEntity() != null ? camera.getFocusedEntity().getStandingEyeHeight() : 1.62;
        double startZ = 0.0;

        double endX = to.x - camPos.x;
        double endY = to.y - camPos.y;
        double endZ = to.z - camPos.z;

        var matrix = matrices.peek().getPositionMatrix();
        lines.vertex(matrix, (float) startX, (float) startY, (float) startZ).color(r, g, b, a).normal(0.0f, 1.0f, 0.0f);
        lines.vertex(matrix, (float) endX, (float) endY, (float) endZ).color(r, g, b, a).normal(0.0f, 1.0f, 0.0f);
    }
}
