package me.nickax.statisticsrewards.data.converter;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OldYamlDataConverter {

    private final StatisticsRewards plugin;

    public OldYamlDataConverter(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void convert() {
        Long start = System.currentTimeMillis();
        int i = 0;
        File folder = new File(plugin.getDataFolder() + "/playerdata");
        if (!folder.exists()) return;
        if (folder.listFiles() == null) return;
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            List<String> receivedRewards = new ArrayList<>();

            ConfigurationSection configurationSection = configuration.getConfigurationSection("received-rewards");
            if (configurationSection != null) {
                receivedRewards.addAll(configurationSection.getKeys(false));
            }
            if (!receivedRewards.isEmpty()) {
                configuration.set("received-rewards", receivedRewards);
                i++;
            }

            try {
                configuration.save(file);
            } catch (Exception e) {
                //
            }
        }
        Long end = System.currentTimeMillis();
        if (i > 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Converted " + i + " old data files successfully in " + (end-start) + "ms!");
        } else if (i == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Converted " + i + " old data file successfully in " + (end-start) + "ms!");
        }
    }
}
