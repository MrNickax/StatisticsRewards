package me.nickax.statisticsrewards.statistic;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.statistic.object.OldStatistic;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class CustomStatisticIncrementTask {

    private final StatisticsRewards plugin;

    private final List<Player> players;

    public CustomStatisticIncrementTask(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.players = new ArrayList<>();
    }

    public void start(Player player) {
        players.add(player);
        new BukkitRunnable() {
            public void run() {
                if (!players.contains(player)) {
                    cancel();
                    return;
                }
                OldStatistic oldStatistic = plugin.getCustomOldStatisticManager().getOldStatistic(player, Statistic.PLAY_ONE_MINUTE);
                if (player.getStatistic(Statistic.PLAY_ONE_MINUTE) > oldStatistic.getAmount()) {
                    Bukkit.getPluginManager().callEvent(new CustomStatisticIncrementEvent(player, Statistic.PLAY_ONE_MINUTE, player.getStatistic(Statistic.PLAY_ONE_MINUTE), oldStatistic.getAmount()));
                    oldStatistic.setAmount(player.getStatistic(Statistic.PLAY_ONE_MINUTE));
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
