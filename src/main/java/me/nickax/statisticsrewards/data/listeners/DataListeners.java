package me.nickax.statisticsrewards.data.listeners;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataListeners implements Listener {

    private final StatisticsRewards plugin;

    public DataListeners(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        plugin.getDataUtils().createData(player);
        plugin.getYamlStorage().restore(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        plugin.getYamlStorage().save(player);
    }
}
