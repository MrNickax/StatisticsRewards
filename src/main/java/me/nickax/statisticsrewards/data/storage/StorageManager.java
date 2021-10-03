package me.nickax.statisticsrewards.data.storage;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.entity.Player;

public class StorageManager {

    private final StatisticsRewards plugin;

    public StorageManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void save(Player player) {
        if (plugin.isMysqlEnabled()) {
            plugin.getMysqlStorageManager().save(player);
        } else {
            plugin.getYamlStorageManager().save(player);
        }
    }

    public void restore(Player player) {
        if (plugin.isMysqlEnabled()) {
            plugin.getMysqlStorageManager().restore(player);
        } else {
            plugin.getYamlStorageManager().restore(player);
        }
    }
}
