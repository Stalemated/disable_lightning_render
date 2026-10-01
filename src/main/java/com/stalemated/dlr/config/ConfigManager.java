package com.stalemated.dlr.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.stalemated.dlr.DisableLightningRender.LOGGER;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Path.of("config", "disable_lightning_render.json");
    private static ConfigManager INSTANCE;

    public boolean disableLightningRender = true;

    public static ConfigManager get() {
        if (INSTANCE == null) load();
        return INSTANCE;
    }

    public static synchronized void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
                INSTANCE = GSON.fromJson(reader, ConfigManager.class);
                if (INSTANCE != null) return;
            } catch (Exception e) {
                LOGGER.error("Failed to read config from {}", CONFIG_PATH, e);
            }
        }
        INSTANCE = new ConfigManager();
        save();
    }

    public static synchronized void save() {
        try {
            Path parent = CONFIG_PATH.getParent();

            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8)) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to save config to {}", CONFIG_PATH, e);
        }
    }
}
