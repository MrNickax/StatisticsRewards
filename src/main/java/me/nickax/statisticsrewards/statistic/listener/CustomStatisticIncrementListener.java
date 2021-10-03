package me.nickax.statisticsrewards.statistic.listener;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.statistic.CustomStatisticIncrementEvent;
import me.nickax.statisticsrewards.statistic.object.OldStatistic;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CustomStatisticIncrementListener implements Listener {

    private final StatisticsRewards plugin;

    public CustomStatisticIncrementListener(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getTo() == null) return;
        if (e.getTo().getX() == e.getFrom().getX() && e.getTo().getY() == e.getFrom().getY() && e.getTo().getZ() == e.getFrom().getZ()) return;

        Player player = e.getPlayer();

        for (Statistic statistic : Statistic.values()) {
            if (plugin.getCustomOldStatisticManager().isUsableStatistic(statistic, true)) {
                OldStatistic oldStatistic = plugin.getCustomOldStatisticManager().getOldStatistic(player, statistic);
                if (player.getStatistic(statistic) > oldStatistic.getAmount()) {
                    Bukkit.getPluginManager().callEvent(new CustomStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), oldStatistic.getAmount()));
                    oldStatistic.setAmount(player.getStatistic(statistic));
                }
            }
        }
    }
}
