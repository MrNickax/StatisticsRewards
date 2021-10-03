package me.nickax.statisticsrewards.config;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.config.enums.ConfigOption;
import me.nickax.statisticsrewards.config.object.ConfigValue;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final StatisticsRewards plugin;

    private final Map<ConfigOption, ConfigValue> configValues;

    public ConfigManager(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.configValues = new HashMap<>();
    }

    public void load() {
        Long start = System.currentTimeMillis();

        FileConfiguration configuration = plugin.getConfigFile().configuration();
        if (configuration == null) return;

        for (ConfigOption configOption : ConfigOption.values()) {
            configValues.put(configOption, new ConfigValue(configuration.get(configOption.getString())));
        }

        if (configValues.isEmpty()) return;

        Long end = System.currentTimeMillis();
        if (configValues.size() > 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + configValues.size() + " configuration values successfully in " + (end-start) + "ms!");
        } else if (configValues.size() == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + configValues.size() + " configuration value successfully in " + (end-start) + "ms!");
        }
    }

    public void reload() {
        configValues.clear();
        load();
    }

    public String getString(ConfigOption configOption) {
        return getConfigValue(configOption).getString();
    }

    public Boolean getBoolean(ConfigOption configOption) {
        return getConfigValue(configOption).getBoolean();
    }

    public Double getDouble(ConfigOption configOption) {
        return getConfigValue(configOption).getDouble();
    }

    public Integer getInteger(ConfigOption configOption) {
        return getConfigValue(configOption).getInteger();
    }

    private ConfigValue getConfigValue(ConfigOption configOption) {
        return configValues.get(configOption);
    }
}
