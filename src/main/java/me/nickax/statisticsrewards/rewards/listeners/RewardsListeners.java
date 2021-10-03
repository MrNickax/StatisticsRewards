package me.nickax.statisticsrewards.rewards.listeners;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.command.enums.CommandType;
import me.nickax.statisticsrewards.data.object.PlayerData;
import me.nickax.statisticsrewards.rewards.object.Command;
import me.nickax.statisticsrewards.rewards.object.Reward;
import me.nickax.statisticsrewards.statistic.CustomStatisticIncrementEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
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
    public void onPlayerStatisticIncrement(PlayerStatisticIncrementEvent e) {
        if (plugin.getCustomOldStatisticManager().isUsableStatistic(e.getStatistic(), false)) return;

        Player player = e.getPlayer();
        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        for (Reward reward : plugin.getRewardsManager().getRewards()) {
            if (!plugin.getCustomOldStatisticManager().isUsableStatistic(reward.getStatistic(), false)) {
                if (!playerData.getReceivedRewards().contains(reward.getName())) {
                    if ((e.getStatistic().getType().equals(Statistic.Type.BLOCK) || e.getStatistic().getType().equals(Statistic.Type.ITEM)) && (reward.getStatistic().getType().equals(Statistic.Type.BLOCK) || reward.getStatistic().getType().equals(Statistic.Type.ITEM))) {
                        if (e.getMaterial() != null && reward.getMaterial() != null) {
                            if (e.getStatistic().equals(reward.getStatistic()) && e.getNewValue() >= reward.getAmount() && e.getMaterial().equals(reward.getMaterial())) {

                                // Play Sound:
                                if (reward.getSound() != null && reward.getPitch() != null) {
                                    player.playSound(player.getLocation(), reward.getSound(), 1, reward.getPitch());
                                }

                                // Send Messages:
                                if (!reward.getMessages().isEmpty()) {
                                    for (String message : reward.getMessages()) {
                                        player.sendMessage(plugin.getTextUtil().color(message).replace("%material%", StringUtils.capitalize(e.getMaterial().name().toLowerCase())).replace("%amount%", String.valueOf(reward.getAmount())));
                                    }
                                }

                                // Execute Commands:
                                if (!reward.getCommands().isEmpty()) {
                                    for (Command command : reward.getCommands()) {
                                        if (command.getCommandType().equals(CommandType.PLAYER)) {
                                            Bukkit.dispatchCommand(player, command.getCommand());
                                        } else if (command.getCommandType().equals(CommandType.CONSOLE)) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.getCommand().replace("%player%", player.getName()));
                                        }
                                    }
                                }

                                // Add reward to data:
                                playerData.addReceivedReward(reward.getName());
                            }
                        }
                    } else if (e.getStatistic().getType().equals(Statistic.Type.ENTITY) && reward.getStatistic().getType().equals(Statistic.Type.ENTITY)) {
                        if (e.getEntityType() != null && reward.getEntityType() != null) {
                            if (e.getStatistic().equals(reward.getStatistic()) && e.getNewValue() >= reward.getAmount() && e.getEntityType().equals(reward.getEntityType())) {

                                // Play Sound:
                                if (reward.getSound() != null && reward.getPitch() != null) {
                                    player.playSound(player.getLocation(), reward.getSound(), 1, reward.getPitch());
                                }

                                // Send Messages:
                                if (!reward.getMessages().isEmpty()) {
                                    for (String message : reward.getMessages()) {
                                        player.sendMessage(plugin.getTextUtil().color(message).replace("%entity%", StringUtils.capitalize(e.getEntityType().name().toLowerCase())).replace("%amount%", String.valueOf(reward.getAmount())));
                                    }
                                }

                                // Execute Commands:
                                if (!reward.getCommands().isEmpty()) {
                                    for (Command command : reward.getCommands()) {
                                        if (command.getCommandType().equals(CommandType.PLAYER)) {
                                            Bukkit.dispatchCommand(player, command.getCommand());
                                        } else if (command.getCommandType().equals(CommandType.CONSOLE)) {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.getCommand().replace("%player%", player.getName()));
                                        }
                                    }
                                }

                                // Add reward to data:
                                playerData.addReceivedReward(reward.getName());
                            }
                        }
                    } else if (e.getStatistic().getType().equals(Statistic.Type.UNTYPED) && reward.getStatistic().getType().equals(Statistic.Type.UNTYPED)) {
                        if (e.getStatistic().equals(reward.getStatistic()) && e.getNewValue() >= reward.getAmount()) {

                            // Play Sound:
                            if (reward.getSound() != null && reward.getPitch() != null) {
                                player.playSound(player.getLocation(), reward.getSound(), 1, reward.getPitch());
                            }

                            // Send Messages:
                            if (!reward.getMessages().isEmpty()) {
                                for (String message : reward.getMessages()) {
                                    player.sendMessage(plugin.getTextUtil().color(message).replace("%amount%", String.valueOf(reward.getAmount())));
                                }
                            }

                            // Execute Commands:
                            if (!reward.getCommands().isEmpty()) {
                                for (Command command : reward.getCommands()) {
                                    if (command.getCommandType().equals(CommandType.PLAYER)) {
                                        Bukkit.dispatchCommand(player, command.getCommand());
                                    } else if (command.getCommandType().equals(CommandType.CONSOLE)) {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.getCommand().replace("%player%", player.getName()));
                                    }
                                }
                            }

                            // Add reward to data:
                            playerData.addReceivedReward(reward.getName());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCustomStatisticIncrement(CustomStatisticIncrementEvent e) {
        if (!plugin.getCustomOldStatisticManager().isUsableStatistic(e.getStatistic(), false)) return;

        Player player = e.getPlayer();
        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        for (Reward reward : plugin.getRewardsManager().getRewards()) {
            if (plugin.getCustomOldStatisticManager().isUsableStatistic(reward.getStatistic(), false)) {
                if (!playerData.getReceivedRewards().contains(reward.getName())) {

                    int newValue;
                    if (e.getStatistic().equals(Statistic.PLAY_ONE_MINUTE)) {
                        newValue = e.getNewValue()/1200;
                    } else {
                        newValue = e.getNewValue();
                    }

                    if (e.getStatistic().equals(reward.getStatistic()) && newValue >= reward.getAmount()) {

                        // Play Sound:
                        if (reward.getSound() != null && reward.getPitch() != null) {
                            player.playSound(player.getLocation(), reward.getSound(), 1, reward.getPitch());
                        }

                        // Send Messages:
                        if (!reward.getMessages().isEmpty()) {
                            for (String message : reward.getMessages()) {
                                if (reward.getStatistic().equals(Statistic.PLAY_ONE_MINUTE)) {
                                    player.sendMessage(plugin.getTextUtil().color(message).replace("%inseconds%", String.valueOf(reward.getAmount()*60)).replace("%inminutes%", String.valueOf(reward.getAmount())).replace("%inhours%", String.valueOf(reward.getAmount()/60)));
                                } else {
                                    player.sendMessage(plugin.getTextUtil().color(message).replace("%amount%", String.valueOf(reward.getAmount())));
                                }
                            }
                        }

                        // Execute Commands:
                        if (!reward.getCommands().isEmpty()) {
                            for (Command command : reward.getCommands()) {
                                if (command.getCommandType().equals(CommandType.PLAYER)) {
                                    Bukkit.dispatchCommand(player, command.getCommand());
                                } else if (command.getCommandType().equals(CommandType.CONSOLE)) {
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.getCommand().replace("%player%", player.getName()));
                                }
                            }
                        }

                        // Add reward to data:
                        playerData.addReceivedReward(reward.getName());
                    }
                }
            }
        }
    }
}
