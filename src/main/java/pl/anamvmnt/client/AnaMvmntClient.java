package pl.anamvmnt.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import pl.anamvmnt.client.config.ConfigManager;
import pl.anamvmnt.client.feature.AnimationController;
import pl.anamvmnt.client.feature.EspRenderer;
import pl.anamvmnt.client.feature.ExtraHudRenderer;
import pl.anamvmnt.client.feature.InventoryHudRenderer;
import pl.anamvmnt.client.screen.AnaMvmntScreen;

public final class AnaMvmntClient implements ClientModInitializer {
    public static final String MOD_ID = "anamvmnt";

    public static ModSettings SETTINGS = new ModSettings();

    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        SETTINGS = ConfigManager.load();

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.anamvmnt.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.anamvmnt.main"
        ));

        InventoryHudRenderer.register();
        ExtraHudRenderer.register();
        EspRenderer.register();
        AnimationController.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                client.setScreen(new AnaMvmntScreen());
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> ConfigManager.save(SETTINGS));
    }
}
