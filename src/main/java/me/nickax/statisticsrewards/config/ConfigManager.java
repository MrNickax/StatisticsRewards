package me.nickax.statisticsrewards.config;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigManager {

    private final StatisticsRewards plugin;
    private final File file;
    private FileConfiguration fileConfiguration;

    public ConfigManager(StatisticsRewards plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "config.yml");
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
        } else if (file.exists()) {
            if (!isValid()) {
                if (file.renameTo(new File(plugin.getDataFolder(), "invalid_config-" + UUID.randomUUID() + ".yml"))) {
                    Bukkit.getLogger().warning("[StatisticsRewards] The config is not valid, a new file has been generated!");
                    plugin.saveResource("config.yml", false);
                    fileConfiguration = YamlConfiguration.loadConfiguration(file);
                }
            } else {
                fileConfiguration = YamlConfiguration.loadConfiguration(file);
            }
        }
    }

    public FileConfiguration config() {
        return fileConfiguration;
    }

    private boolean isValid() {
        InputStream inputStream = plugin.getResource("config.yml");
        AtomicBoolean isValid = new AtomicBoolean(true);
        if (inputStream != null) {
            FileConfiguration valid = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
            valid.getKeys(false).forEach(section -> {
                if (!section.contains("rewards")) {
                    if (!config().contains(section)) {
                        isValid.set(false);
                    }
                }
            });
        }
        return isValid.get();
    }
}
