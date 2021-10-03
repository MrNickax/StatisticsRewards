package me.nickax.statisticsrewards.data.listener;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.object.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataCreationListeners implements Listener {

    private final StatisticsRewards plugin;

    public DataCreationListeners(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData != null) return;

        plugin.getDataManager().addPlayerData(player);
        plugin.getStorageManager().restore(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        plugin.getStorageManager().save(player);
        plugin.getDataManager().removePlayerData(player);
    }
}
