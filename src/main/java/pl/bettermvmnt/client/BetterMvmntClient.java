package pl.bettermvmnt.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import pl.bettermvmnt.client.config.ConfigManager;
import pl.bettermvmnt.client.feature.AnimationController;
import pl.bettermvmnt.client.feature.EspRenderer;
import pl.bettermvmnt.client.feature.ExtraHudRenderer;
import pl.bettermvmnt.client.feature.InventoryHudRenderer;
import pl.bettermvmnt.client.feature.HatRenderer;
import pl.bettermvmnt.client.screen.BetterIntroScreen;

public final class BetterMvmntClient implements ClientModInitializer {
    public static final String MOD_ID = "bettermvmnt";

    public static ModSettings SETTINGS = new ModSettings();

    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        SETTINGS = ConfigManager.load();

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.bettermvmnt.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.bettermvmnt.main"
        ));

        InventoryHudRenderer.register();
        ExtraHudRenderer.register();
        EspRenderer.register();
        HatRenderer.register();
        AnimationController.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                client.setScreen(new BetterIntroScreen());
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> ConfigManager.save(SETTINGS));
    }
}
