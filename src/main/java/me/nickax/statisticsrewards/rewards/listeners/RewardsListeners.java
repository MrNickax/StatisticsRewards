package me.nickax.statisticsrewards.rewards.listeners;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.rewards.format.Reward;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;

public class RewardsListeners implements Listener {

    private final StatisticsRewards plugin;

    public RewardsListeners(StatisticsRewards plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onIncrement(PlayerStatisticIncrementEvent e) {
        Player p = e.getPlayer();
        if (plugin.getRewardsManager().getRewardList().isEmpty()) return;
        for (Reward reward : plugin.getRewardsManager().getRewardList()) {
            DataManager dataManager = plugin.getPlayerData().getPlayer(p);
            if (dataManager == null) return;
            if (e.getStatistic().getType().equals(Statistic.Type.BLOCK) || e.getStatistic().getType().equals(Statistic.Type.ITEM)) {
                if (reward.getStatistic().getType().equals(Statistic.Type.BLOCK) || reward.getStatistic().getType().equals(Statistic.Type.ITEM)) {
                    if (e.getStatistic().equals(reward.getStatistic()) && e.getMaterial() == reward.getMaterial()) {
                        if (e.getNewValue() >= reward.getAmount()) {
                            if (dataManager.isClaimed(reward.getRewardID())) return;
                            if (e.getMaterial() == null) return;
                            if (reward.isSound()) {
                                String sound = plugin.getConfigManager().config().getString("rewards." + reward.getRewardID() + ".play-sound" + ".sound");
                                String pitch = plugin.getConfigManager().config().getString("rewards." + reward.getRewardID() + ".play-sound" + ".pitch");
                                if (sound != null && pitch != null) {
                                    p.playSound(p.getLocation(), Sound.valueOf(sound), 1, Integer.parseInt(pitch));
                                }
                            }
                            if (reward.isMessage()) {
                                for (String message : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".messages")) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", "" + reward.getAmount()).replace("%material%", StringUtils.capitalize(e.getMaterial().toString().toLowerCase().replace("_", " ")))));
                                }
                            }
                            if (reward.isPlayerCommand()) {
                                for (String command : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".commands" + ".player")) {
                                    Bukkit.dispatchCommand(p, command);
                                }
                            }
                            if (reward.isConsoleCommand()) {
                                for (String command : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".commands" + ".console")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", p.getName()));
                                }
                            }
                            dataManager.setCollectedRewards(reward.getRewardID(), true);
                        }
                    }
                }
            } else if (e.getStatistic().getType().equals(Statistic.Type.ENTITY)) {
                if (reward.getStatistic().getType().equals(Statistic.Type.ENTITY)) {
                    if (e.getStatistic().equals(reward.getStatistic()) || e.getEntityType() == reward.getEntityType()) {
                        if (e.getNewValue() >= reward.getAmount()) {
                            if (dataManager.isClaimed(reward.getRewardID())) return;
                            if (e.getEntityType() == null) return;
                            if (reward.isSound()) {
                                String sound = plugin.getConfigManager().config().getString("rewards." + reward.getRewardID() + ".play-sound" + ".sound");
                                String pitch = plugin.getConfigManager().config().getString("rewards." + reward.getRewardID() + ".play-sound" + ".pitch");
                                if (sound != null && pitch != null) {
                                    p.playSound(p.getLocation(), Sound.valueOf(sound), 1, Integer.parseInt(pitch));
                                }
                            }
                            if (reward.isMessage()) {
                                for (String message : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".messages")) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", "" + reward.getAmount()).replace("%entity%", StringUtils.capitalize(e.getEntityType().toString().toLowerCase().replace("_", " ")))));
                                }
                            }
                            if (reward.isPlayerCommand()) {
                                for (String command : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".commands" + ".player")) {
                                    Bukkit.dispatchCommand(p, command);
                                }
                            }
                            if (reward.isConsoleCommand()) {
                                for (String command : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".commands" + ".console")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", p.getName()));
                                }
                            }
                            dataManager.setCollectedRewards(reward.getRewardID(), true);
                        }
                    }
                }
            } else if (e.getStatistic().getType().equals(Statistic.Type.UNTYPED)) {
                if (reward.getStatistic().getType().equals(Statistic.Type.UNTYPED)) {
                    if (e.getStatistic().equals(reward.getStatistic())) {
                        if (e.getNewValue() >= reward.getAmount()) {
                            if (dataManager.isClaimed(reward.getRewardID())) return;
                            if (reward.isSound()) {
                                String sound = plugin.getConfigManager().config().getString("rewards." + reward.getRewardID() + ".play-sound" + ".sound");
                                String pitch = plugin.getConfigManager().config().getString("rewards." + reward.getRewardID() + ".play-sound" + ".pitch");
                                if (sound != null && pitch != null) {
                                    p.playSound(p.getLocation(), Sound.valueOf(sound), 1, Integer.parseInt(pitch));
                                }
                            }
                            if (reward.isMessage()) {
                                for (String message : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".messages")) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", "" + reward.getAmount())));
                                }
                            }
                            if (reward.isPlayerCommand()) {
                                for (String command : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".commands" + ".player")) {
                                    Bukkit.dispatchCommand(p, command);
                                }
                            }
                            if (reward.isConsoleCommand()) {
                                for (String command : plugin.getConfig().getStringList("rewards." + reward.getRewardID() + ".commands" + ".console")) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", p.getName()));
                                }
                            }
                            dataManager.setCollectedRewards(reward.getRewardID(), true);
                        }
                    }
                }
            }
        }
    }
}
