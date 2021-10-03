package me.nickax.statisticsrewards.config;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class ConfigFile {

    private final StatisticsRewards plugin;

    private final File file;
    private FileConfiguration fileConfiguration;

    public ConfigFile(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder() + "/config.yml");
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        Long start = System.currentTimeMillis();
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        } else {
            InputStream inputStream = plugin.getResource("config.yml");
            if (inputStream != null) {
                YamlConfiguration defaultConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
                if (!plugin.getFileUtil().isValid(file, fileConfiguration, defaultConfiguration)) {
                    String ID = String.valueOf(UUID.randomUUID()).split("-")[0];
                    if (file.renameTo(new File(plugin.getDataFolder() + "/invalid-config-" + ID + ".yml"))) {
                        plugin.saveResource("config.yml", false);
                        Bukkit.getLogger().warning("[StatisticsRewards] The config file was not valid, a new file has been generated!");
                        Bukkit.getLogger().warning("                    The old config file name is now: invalid-config-" + ID);
                        Bukkit.getLogger().warning("                    Invalid reason: " + plugin.getFileUtil().getInvalidReason());
                    }
                }
            }
        }
        reload();
        Long end = System.currentTimeMillis();
        Bukkit.getLogger().info("[StatisticsRewards] Configuration loaded successfully in " + (end-start) + "ms!");
    }

    public void reload() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration configuration() {
        return fileConfiguration;
    }
}
