package me.nickax.statisticsrewards.data.storage;

import me.nickax.statisticsrewards.StatisticsRewards;

import me.nickax.statisticsrewards.data.object.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class YamlStorageManager {

    private final StatisticsRewards plugin;

    public YamlStorageManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void save(Player player) {

        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        configuration.set("name", player.getName());
        configuration.set("uuid", player.getUniqueId().toString());

        if (playerData.getLanguage() != null) {
            configuration.set("language", playerData.getLanguage());
        }

        if (!playerData.getReceivedRewards().isEmpty()) {
            configuration.set("received-rewards", playerData.getReceivedRewards().toArray());
        }

        try {
            configuration.save(file);
        } catch (Exception e) {
            //
        }
    }

    public void restore(Player player) {

        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if (configuration.contains("language")) {
            playerData.setLanguage(configuration.getString("language"));
        }

        if (configuration.contains("received-rewards")) {
            for (String reward : configuration.getStringList("received-rewards")) {
                playerData.addReceivedReward(reward);
            }
        }
    }
}
