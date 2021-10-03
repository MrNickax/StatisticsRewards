package me.nickax.statisticsrewards.util;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

public class TextUtil {

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public String generateBar(int progress, int goal, int size, String symbol) {
        float percent = (float) progress / goal;
        if (percent > 1) {
            percent = 1;
        }
        int bars = (int) (size * percent);
        return Strings.repeat("" + ChatColor.GREEN + symbol, bars) + Strings.repeat("" + ChatColor.RED + symbol, size - bars);
    }
}
