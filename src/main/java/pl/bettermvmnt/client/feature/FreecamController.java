package pl.bettermvmnt.client.feature;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import pl.bettermvmnt.client.BetterMvmntClient;

public final class FreecamController {
    private static boolean applied;
    private static boolean previousAllowFlying;
    private static boolean previousFlying;
    private static float previousFlySpeed;

    private FreecamController() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(FreecamController::tick);
    }

    private static void tick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (BetterMvmntClient.SETTINGS.freecamEnabled && !applied) {
            previousAllowFlying = player.getAbilities().allowFlying;
            previousFlying = player.getAbilities().flying;
            previousFlySpeed = player.getAbilities().getFlySpeed();

            player.getAbilities().allowFlying = true;
            player.getAbilities().flying = true;
            player.getAbilities().setFlySpeed(0.12F);
            applied = true;
        } else if (!BetterMvmntClient.SETTINGS.freecamEnabled && applied) {
            player.getAbilities().allowFlying = previousAllowFlying;
            player.getAbilities().flying = previousFlying;
            player.getAbilities().setFlySpeed(previousFlySpeed);
            applied = false;
        }
    }
}
