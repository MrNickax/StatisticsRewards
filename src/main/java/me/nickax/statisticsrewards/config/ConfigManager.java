package me.nickax.statisticsrewards.config;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class ConfigManager {

    private final StatisticsRewards plugin;

    public ConfigManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void load() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        } else if (file.exists()) {
            if (!checkValid()) {
                file.renameTo(new File(plugin.getDataFolder(), "invalid-config-" + UUID.randomUUID() + ".yml"));
                plugin.saveResource("config.yml", false);
                Bukkit.getLogger().warning("[StatisticsRewards] The config file is not valid, a new file as been generated!");
            }
        }
    }

    public void reload() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.reloadConfig();
        }
    }

    public FileConfiguration config() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    private boolean checkValid() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (file.exists()) {
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
            return fileConfiguration.contains("language") && fileConfiguration.contains("check-for-updates") && fileConfiguration.contains("data-auto-save") && fileConfiguration.contains("data-save-delay");
        }
        return false;
    }

}
