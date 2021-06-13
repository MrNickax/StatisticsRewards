package me.nickax.statisticsrewards.config;

import me.nickax.statisticsrewards.StatisticsRewards;

import java.io.File;

public class ConfigManager {

    private final StatisticsRewards plugin;
    private final File folder;

    public ConfigManager(StatisticsRewards plugin) {
        this.plugin = plugin;
        folder = new File(plugin.getDataFolder() + "");
    }

    public void load() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
    }
    public void reload() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (file.exists()) {
            plugin.reloadConfig();
        }
    }
}
