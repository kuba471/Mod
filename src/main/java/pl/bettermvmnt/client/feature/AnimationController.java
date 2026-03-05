package pl.bettermvmnt.client.feature;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import pl.bettermvmnt.client.BetterMvmntClient;

public final class AnimationController {
    private AnimationController() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Hook point for animation logic.
            // In this base version settings are toggled in GUI and can be consumed by mixins/render layers.
            if (client.player == null) {
                return;
            }

            if (BetterMvmntClient.SETTINGS.freezeLegAnimation) {
                // intentionally lightweight: visual animation modules can read this state
            }

            if (BetterMvmntClient.SETTINGS.swordSwingDownStyle) {
                // intentionally lightweight: sword animation modules can read this state
            }
        });
    }
}
