package me.nickax.statisticsrewards.rewards;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class RewardsFile {

    private final StatisticsRewards plugin;

    private final File file;
    private FileConfiguration fileConfiguration;

    public RewardsFile(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder() + "/rewards.yml");
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        if (!file.exists()) {
            plugin.saveResource("rewards.yml", false);
        }
        reload();
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration configuration() {
        return fileConfiguration;
    }
}
