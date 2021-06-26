package me.nickax.statisticsrewards.data;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.entity.Player;

public class DataUtils {

    private final StatisticsRewards plugin;

    public DataUtils(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void createData(Player player) {
        DataManager playerData = plugin.getPlayerManager().getPlayer(player);
        if (playerData == null) {
            DataManager dataManager = new DataManager(player);
            plugin.getPlayerManager().addData(player, dataManager);
        }
    }
}
