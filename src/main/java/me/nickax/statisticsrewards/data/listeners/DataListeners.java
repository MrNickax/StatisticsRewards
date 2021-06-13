package me.nickax.statisticsrewards.data.listeners;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DataListeners implements Listener {

    private final StatisticsRewards plugin;

    public DataListeners(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDataCreate(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        DataManager playerData = plugin.getPlayerData().getPlayer(player);
        if (playerData == null) {
            plugin.getDataUtils().createData(player);
        }
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onDataRestore(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        DataManager playerData = plugin.getPlayerData().getPlayer(player);
        if (playerData != null) {
            if (plugin.getYamlStorage().config(player).contains("collected-rewards")) {
                plugin.getYamlStorage().restore(player);
            }
        }
    }
}
