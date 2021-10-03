package me.nickax.statisticsrewards.lang.listeners;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.config.enums.ConfigOption;
import me.nickax.statisticsrewards.data.object.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LangJoinListeners implements Listener {

    private final StatisticsRewards plugin;

    public LangJoinListeners(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (plugin.getConfigManager().getBoolean(ConfigOption.DETECT_CLIENT_LANGUAGE_ENABLED)) {
            Player player = e.getPlayer();
            new BukkitRunnable() {
                public void run() {
                    PlayerData playerData = plugin.getDataManager().getPlayerData(player);
                    if (playerData != null && playerData.getLanguage() == null) {
                        if (plugin.getLangFile().contains(player.getLocale().split("_")[0])) {
                            plugin.getLangManager().setLanguage(player, player.getLocale().split("_")[0]);
                        }
                    }
                }
            }.runTaskLaterAsynchronously(plugin, 20L);
        }
    }
}
