package me.nickax.statisticsrewards.commands.plugin_commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.commands.CommandBuilder;
import me.nickax.statisticsrewards.data.DataManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetUncollectedCommand extends CommandBuilder {

    private final StatisticsRewards plugin = StatisticsRewards.getPlugin(StatisticsRewards.class);

    @Override
    public String command() {
        return "set";
    }

    @Override
    public String syntax() {
        return "uncollected";
    }

    @Override
    public String permission() {
        return "statisticsrewards.set.uncollected";
    }

    @Override
    public String description() {
        return "- set a reward uncollected.";
    }

    @Override
    public String usage() {
        return "/statisticsrewards set uncollected " + ChatColor.DARK_GREEN + "<player> <reward-name>";
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
            DataManager dataManager = plugin.getPlayerManager().getPlayer(target);
            if (dataManager == null) {
                plugin.getMessageManager().dataNotFound(sender, target);
                return;
            }
            if (!dataManager.isReceived(args[3])) {
                plugin.getMessageManager().rewardNotFound(sender, args[3]);
                return;
            }
            dataManager.setReceivedReward(args[3], false);
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p == target) {
                    plugin.getMessageManager().setUncollectedTarget(p, args[3]);
                }
                if (p != target) {
                    plugin.getMessageManager().setUncollected(sender, args[3], target);
                    plugin.getMessageManager().setUncollectedTarget(target, args[3]);
                }
            } else if (sender instanceof ConsoleCommandSender) {
                plugin.getMessageManager().setUncollected(sender, args[3], target);
                plugin.getMessageManager().setUncollectedTarget(target, args[3]);
            }
        }
    }
}
