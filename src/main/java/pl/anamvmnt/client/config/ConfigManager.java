package pl.anamvmnt.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import pl.anamvmnt.client.ModSettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("anamvmnt.json");

    private ConfigManager() {
    }

    public static ModSettings load() {
        if (!Files.exists(CONFIG_PATH)) {
            return new ModSettings();
        }

        try {
            String json = Files.readString(CONFIG_PATH);
            ModSettings loaded = GSON.fromJson(json, ModSettings.class);
            return loaded == null ? new ModSettings() : loaded;
        } catch (IOException ex) {
            return new ModSettings();
        }
    }

    public static void save(ModSettings settings) {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(settings));
        } catch (IOException ignored) {
        }
    }
}
