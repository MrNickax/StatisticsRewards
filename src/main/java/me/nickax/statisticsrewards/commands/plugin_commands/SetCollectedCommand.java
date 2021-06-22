package me.nickax.statisticsrewards.commands.plugin_commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.commands.CommandBuilder;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.rewards.format.Reward;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetCollectedCommand extends CommandBuilder {

    private final StatisticsRewards plugin = StatisticsRewards.getPlugin(StatisticsRewards.class);

    @Override
    public String command() {
        return "set";
    }

    @Override
    public String syntax() {
        return "collected";
    }

    @Override
    public String permission() {
        return "statisticsrewards.set.collected";
    }

    @Override
    public String description() {
        return "- set a reward collected.";
    }

    @Override
    public String usage() {
        return "/statisticsrewards set collected " + ChatColor.DARK_GREEN + "<player> <reward-name>";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission(permission())) {
                plugin.getMessageManager().noPermission(sender);
                return;
            }
        }
        if (args.length == 4) {
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                plugin.getMessageManager().playerNotFound(sender, StringUtils.capitalize(args[2].toLowerCase()));
                return;
            }
            DataManager dataManager = plugin.getPlayerData().getPlayer(target);
            if (dataManager == null) {
                plugin.getMessageManager().dataNotFound(sender, target);
                return;
            }
            for (Reward reward : plugin.getRewardsManager().getRewardList()) {
                if (reward.getRewardID().equalsIgnoreCase(args[3])) {
                    dataManager.setCollectedRewards(args[3], true);
                    if (sender instanceof Player) {
                        Player p = (Player) sender;
                        if (p == target) {
                            plugin.getMessageManager().setCollectedTarget(p, args[3]);
                        }
                        if (p != target) {
                            plugin.getMessageManager().setCollected(sender, args[3], target);
                            plugin.getMessageManager().setCollectedTarget(target, args[3]);
                        }
                    } else if (sender instanceof ConsoleCommandSender) {
                        plugin.getMessageManager().setCollected(sender, args[3], target);
                        plugin.getMessageManager().setCollectedTarget(target, args[3]);
                    }
                    return;
                }
            }
            plugin.getMessageManager().rewardNotFound(sender, args[3]);
        }
    }
}