package pl.anamvmnt.client.feature;

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

        for (Entity entity : client.world.getEntities()) {
            if (!(entity instanceof PlayerEntity) || entity == client.player) {
                continue;
            }

            Box box = entity.getBoundingBox().offset(-camPos.x, -camPos.y, -camPos.z).expand(0.02);
            VertexRendering.drawBox(matrices, lines, box, r, g, b, a);
        }

        immediate.draw();
    }
}
