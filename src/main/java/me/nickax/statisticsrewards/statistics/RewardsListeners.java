package me.nickax.statisticsrewards.statistics;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

import java.util.Map;

public class RewardsListeners implements Listener {

    private final StatisticsRewards plugin;

    public RewardsListeners(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerStatisticIncrementEvent e) {
        Player p = e.getPlayer();
        for (Map.Entry<String, Statistic.Type> entry : plugin.getRewardsManager().getLoadedRewards().entrySet()) {
            if (e.getStatistic().getType().equals(entry.getValue())) {
                if (entry.getValue().equals(Statistic.Type.BLOCK)) {
                    Material material = Material.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".material"));
                    Statistic statistic = Statistic.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".statistic"));
                    int amount = plugin.getConfig().getInt("rewards." + entry.getKey() + ".amount");
                    if (e.getStatistic().equals(statistic) && e.getMaterial() == material) {
                        if (e.getNewValue() >= amount) {
                            DataManager dataManager = plugin.getPlayerData().getPlayer(p);
                            if (dataManager != null) {
                                if (!dataManager.getCollectedRewards().containsKey(entry.getKey())) {
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".messages")) {
                                        for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".messages")) {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', string).replace("%amount%", "" + amount).replace("%material%", StringUtils.capitalize(material.toString().toLowerCase()).replace("_", " ")));
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands")) {
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                                Bukkit.dispatchCommand(p, string);
                                            }
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".play-sound")) {
                                        Sound sound = Sound.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".play-sound" + ".sound"));
                                        int pitch = plugin.getConfig().getInt("rewards." + entry.getKey() + ".play-sound" + ".pitch");
                                        p.playSound(p.getLocation(), sound, 1, pitch);
                                    }
                                    dataManager.setCollectedRewards(entry.getKey(), true);
                                }
                            }
                        }
                    }
                } else if (entry.getValue().equals(Statistic.Type.ITEM)) {
                    Material material = Material.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".material"));
                    Statistic statistic = Statistic.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".statistic"));
                    int amount = plugin.getConfig().getInt("rewards." + entry.getKey() + ".amount");
                    if (e.getStatistic().equals(statistic) && e.getMaterial() == material) {
                        if (e.getNewValue() >= amount) {
                            DataManager dataManager = plugin.getPlayerData().getPlayer(p);
                            if (dataManager != null) {
                                if (!dataManager.getCollectedRewards().containsKey(entry.getKey())) {
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".messages")) {
                                        for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".messages")) {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', string).replace("%amount%", "" + amount).replace("%material%", StringUtils.capitalize(material.toString().toLowerCase()).replace("_", " ")));
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands")) {
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                                Bukkit.dispatchCommand(p, string);
                                            }
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".play-sound")) {
                                        Sound sound = Sound.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".play-sound" + ".sound"));
                                        int pitch = plugin.getConfig().getInt("rewards." + entry.getKey() + ".play-sound" + ".pitch");
                                        p.playSound(p.getLocation(), sound, 1, pitch);
                                    }
                                    dataManager.setCollectedRewards(entry.getKey(), true);
                                }
                            }
                        }
                    }
                } else if (entry.getValue().equals(Statistic.Type.ENTITY)) {
                    EntityType entity = EntityType.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".entity"));
                    Statistic statistic = Statistic.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".statistic"));
                    int amount = plugin.getConfig().getInt("rewards." + entry.getKey() + ".amount");
                    if (e.getStatistic().equals(statistic) && e.getEntityType() == entity) {
                        if (e.getNewValue() >= amount) {
                            DataManager dataManager = plugin.getPlayerData().getPlayer(p);
                            if (dataManager != null) {
                                if (!dataManager.getCollectedRewards().containsKey(entry.getKey())) {
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".messages")) {
                                        for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".messages")) {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', string).replace("%amount%", "" + amount).replace("%entity%", StringUtils.capitalize(entity.toString().toLowerCase()).replace("_", " ")));
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands")) {
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                                Bukkit.dispatchCommand(p, string);
                                            }
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".play-sound")) {
                                        Sound sound = Sound.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".play-sound" + ".sound"));
                                        int pitch = plugin.getConfig().getInt("rewards." + entry.getKey() + ".play-sound" + ".pitch");
                                        p.playSound(p.getLocation(), sound, 1, pitch);
                                    }
                                    dataManager.setCollectedRewards(entry.getKey(), true);
                                }
                            }
                        }
                    }
                } else if (entry.getValue().equals(Statistic.Type.UNTYPED)) {
                    Statistic statistic = Statistic.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".statistic"));
                    int amount = plugin.getConfig().getInt("rewards." + entry.getKey() + ".amount");
                    if (e.getStatistic().equals(statistic)) {
                        if (e.getNewValue() >= amount) {
                            DataManager dataManager = plugin.getPlayerData().getPlayer(p);
                            if (dataManager != null) {
                                if (!dataManager.getCollectedRewards().containsKey(entry.getKey())) {
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".messages")) {
                                        for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".messages")) {
                                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', string).replace("%amount%", "" + amount));
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands")) {
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".console-commands")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", p.getName()));
                                            }
                                        }
                                        if (plugin.getConfig().contains("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                            for (String string : plugin.getConfig().getStringList("rewards." + entry.getKey() + ".commands" + ".player-commands")) {
                                                Bukkit.dispatchCommand(p, string);
                                            }
                                        }
                                    }
                                    if (plugin.getConfig().contains("rewards." + entry.getKey() + ".play-sound")) {
                                        Sound sound = Sound.valueOf(plugin.getConfig().getString("rewards." + entry.getKey() + ".play-sound" + ".sound"));
                                        int pitch = plugin.getConfig().getInt("rewards." + entry.getKey() + ".play-sound" + ".pitch");
                                        p.playSound(p.getLocation(), sound, 1, pitch);
                                    }
                                    dataManager.setCollectedRewards(entry.getKey(), true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
