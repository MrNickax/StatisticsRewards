package me.nickax.statisticsrewards.statistic.listener;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CustomStatisticIncrementInitialization implements Listener {

    private final StatisticsRewards plugin;

    public CustomStatisticIncrementInitialization(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        plugin.getCustomOldStatisticManager().load(player);
        plugin.getCustomStatisticIncrementTask().start(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        plugin.getCustomStatisticIncrementTask().removePlayer(player);
        plugin.getCustomOldStatisticManager().unload(player);
    }
}
