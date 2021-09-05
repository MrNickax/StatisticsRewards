package me.nickax.statisticsrewards.util;

import com.google.common.base.Strings;
import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.ChatColor;

public class ProgressBar {

    private final StatisticsRewards plugin;

    public ProgressBar(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public String create(int progress, int goal, int size, String symbol) {
        float percent = (float) progress / goal;
        if (percent > 1) {
            percent = 1;
        }
        int bars = (int) (size * percent);
        return Strings.repeat("" + ChatColor.GREEN + symbol, bars) + Strings.repeat("" + ChatColor.RED + symbol, size - bars);
    }
}
