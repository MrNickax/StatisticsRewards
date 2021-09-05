package me.nickax.statisticsrewards.data.storage;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;

public class YamlStorage {

    private final StatisticsRewards plugin;
    private final File folder;

    public YamlStorage(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder() + "/playerdata/");
    }

    public void save(Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/", player.getUniqueId() + ".yml");
        DataManager dataManager = plugin.getPlayerManager().getPlayer(player);
        if (dataManager == null) return;
        if (dataManager.getReceivedRewards().isEmpty()) return;
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                Bukkit.getLogger().warning("[StatisticsRewards] An error has occurred creating the data folder, if you see this error, report it to the developer of your player data could be in danger!");
                return;
            }
        }
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        try {
            fileConfiguration.set("uuid", player.getUniqueId().toString());
            fileConfiguration.set("name", player.getName());
            if (!dataManager.getReceivedRewards().isEmpty()) {
                for (Map.Entry<String, Boolean> reward : dataManager.getReceivedRewards().entrySet()) {
                    if (!fileConfiguration.getBoolean("received-rewards." + reward.getKey()) == reward.getValue()) {
                        fileConfiguration.set("received-rewards." + reward.getKey(), reward.getValue());
                        fileConfiguration.save(file);
                    }
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("[StatisticsRewards] An error has occurred saving the " + player.getName() + " data, if you see this error, report it to the developer of your player data could be in danger!");
        }
    }

    public void restore(Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/", player.getUniqueId() + ".yml");
        DataManager dataManager = plugin.getPlayerManager().getPlayer(player);
        if (dataManager == null) return;
        if (!file.exists()) return;
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection rewards = fileConfiguration.getConfigurationSection("received-rewards");
        if (rewards != null) {
            rewards.getKeys(false).forEach(reward -> {
                Boolean received = fileConfiguration.getBoolean("received-rewards." + reward);
                dataManager.setReceivedReward(reward, received);
            });
        }
    }
}
