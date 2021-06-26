package me.nickax.statisticsrewards.rewards.listeners;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.rewards.object.Reward;
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
    public void onIncrease(PlayerStatisticIncrementEvent e) {
        Player player = e.getPlayer();
        DataManager dataManager = plugin.getPlayerManager().getPlayer(player);
        if (dataManager == null) return;
        if (plugin.getRewardManager().getRewardList().isEmpty()) return;
        for (Reward reward : plugin.getRewardManager().getRewardList()) {
            if (reward.getStatistic().getType().equals(Statistic.Type.BLOCK) || reward.getStatistic().getType().equals(Statistic.Type.ITEM)) {
                if (e.getStatistic().getType().equals(Statistic.Type.BLOCK) || e.getStatistic().getType().equals(Statistic.Type.ITEM)) {
                    if (reward.getMaterial() != null) {
                        if (e.getStatistic().equals(reward.getStatistic())) {
                            if (e.getNewValue() >= reward.getAmount()) {
                                if (!dataManager.isReceived(reward.getName())) {
                                    if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".play-sound")) {
                                        Sound sound = Sound.valueOf(plugin.getConfigManager().config().getString("rewards." + reward.getName() + ".play-sound" + ".sound"));
                                        int pitch = plugin.getConfigManager().config().getInt("rewards." + reward.getName() + ".play-sound" + ".pitch");
                                        player.playSound(player.getLocation(), sound, 1, pitch);
                                    }
                                    if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".messages")) {
                                        for (String message : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".messages")) {
                                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", "" + reward.getAmount()).replace("%material%", StringUtils.capitalize(reward.getMaterial().toString().toLowerCase().replace("_", " ")))));
                                        }
                                    }
                                    if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands")) {
                                        if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands" + ".console")) {
                                            for (String command : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".commands" + ".console")) {
                                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                                            }
                                        }
                                        if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands" + ".player")) {
                                            for (String command : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".commands" + ".player")) {
                                                Bukkit.dispatchCommand(player, command);
                                            }
                                        }
                                    }
                                    dataManager.setReceivedReward(reward.getName(), true);
                                }
                            }
                        }
                    }
                }
            } else if (reward.getStatistic().getType().equals(Statistic.Type.ENTITY) && e.getStatistic().getType().equals(Statistic.Type.ENTITY)) {
                if (reward.getEntityType() != null) {
                    if (e.getStatistic().equals(reward.getStatistic())) {
                        if (e.getNewValue() >= reward.getAmount()) {
                            if (!dataManager.isReceived(reward.getName())) {
                                if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".play-sound")) {
                                    Sound sound = Sound.valueOf(plugin.getConfigManager().config().getString("rewards." + reward.getName() + ".play-sound" + ".sound"));
                                    int pitch = plugin.getConfigManager().config().getInt("rewards." + reward.getName() + ".play-sound" + ".pitch");
                                    player.playSound(player.getLocation(), sound, 1, pitch);
                                }
                                if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".messages")) {
                                    for (String message : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".messages")) {
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", "" + reward.getAmount()).replace("%entity%", StringUtils.capitalize(reward.getEntityType().toString().toLowerCase().replace("_", " ")))));
                                    }
                                }
                                if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands")) {
                                    if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands" + ".console")) {
                                        for (String command : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".commands" + ".console")) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                                        }
                                    }
                                    if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands" + ".player")) {
                                        for (String command : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".commands" + ".player")) {
                                            Bukkit.dispatchCommand(player, command);
                                        }
                                    }
                                }
                                dataManager.setReceivedReward(reward.getName(), true);
                            }
                        }
                    }
                }
            } else if (reward.getStatistic().getType().equals(Statistic.Type.UNTYPED) && e.getStatistic().getType().equals(Statistic.Type.UNTYPED)) {
                if (reward.getEntityType() == null && reward.getMaterial() == null) {
                    if (e.getStatistic().equals(reward.getStatistic())) {
                        if (e.getNewValue() >= reward.getAmount()) {
                            if (!dataManager.isReceived(reward.getName())) {
                                if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".play-sound")) {
                                    Sound sound = Sound.valueOf(plugin.getConfigManager().config().getString("rewards." + reward.getName() + ".play-sound" + ".sound"));
                                    int pitch = plugin.getConfigManager().config().getInt("rewards." + reward.getName() + ".play-sound" + ".pitch");
                                    player.playSound(player.getLocation(), sound, 1, pitch);
                                }
                                if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".messages")) {
                                    for (String message : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".messages")) {
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("%amount%", "" + reward.getAmount())));
                                    }
                                }
                                if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands")) {
                                    if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands" + ".console")) {
                                        for (String command : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".commands" + ".console")) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                                        }
                                    }
                                    if (plugin.getConfigManager().config().contains("rewards." + reward.getName() + ".commands" + ".player")) {
                                        for (String command : plugin.getConfigManager().config().getStringList("rewards." + reward.getName() + ".commands" + ".player")) {
                                            Bukkit.dispatchCommand(player, command);
                                        }
                                    }
                                }
                                dataManager.setReceivedReward(reward.getName(), true);
                            }
                        }
                    }
                }
            }
        }
    }
}
