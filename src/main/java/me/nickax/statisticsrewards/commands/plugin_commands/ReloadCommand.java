package me.nickax.statisticsrewards.commands.plugin_commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.commands.CommandBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends CommandBuilder {

    private final StatisticsRewards plugin = StatisticsRewards.getPlugin(StatisticsRewards.class);

    @Override
    public String command() {
        return "reload";
    }

    @Override
    public String syntax() {
        return null;
    }

    @Override
    public String permission() {
        return "statisticsrewards.reload";
    }

    @Override
    public String description() {
        return "- reload the config files.";
    }

    @Override
    public String usage() {
        return "/statisticsrewards reload";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission(permission())) {
                plugin.getMessageManager().noPermission(p);
                return;
            }
        }
        if (args.length == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Reloading the config...");
            plugin.getConfigManager().reload();
            plugin.getRewardsManager().load();
            plugin.getLangManager().load();
            plugin.getMessageManager().reload(sender);
        }
    }
}
