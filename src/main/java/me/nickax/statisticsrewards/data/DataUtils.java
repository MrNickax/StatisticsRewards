package me.nickax.statisticsrewards.data;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DataUtils {

    private final StatisticsRewards plugin;

    public DataUtils(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void createData(Player player) {
        try {
            DataManager dataManager = new DataManager(player, plugin);
            plugin.getPlayerData().addPlayerData(player, dataManager);
        } catch (Exception e) {
            Bukkit.getLogger().warning("[StatisticsRewards] An error has occurred creating data for " + player.getName() + ", if you see this error, report it to the developer or the data of your players could be in danger!");
        }
    }
}
