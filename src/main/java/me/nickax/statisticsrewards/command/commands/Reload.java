package me.nickax.statisticsrewards.command.commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.command.enums.CommandType;
import me.nickax.statisticsrewards.command.object.CommandL;
import me.nickax.statisticsrewards.lang.enums.LangOption;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class Reload extends CommandL {

    private final StatisticsRewards plugin;

    public Reload(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("reload");
    }

    @Override
    public String subCommand() {
        return null;
    }

    @Override
    public String permission() {
        return "statisticsrewards.reload";
    }

    @Override
    public boolean hide() {
        return false;
    }

    @Override
    public CommandType commandType() {
        return CommandType.ALL;
    }

    @Override
    public int minArguments() {
        return 1;
    }

    @Override
    public int maxArguments() {
        return 1;
    }

    @Override
    public String description() {
        return "reload the configuration files.";
    }

    @Override
    public String usage() {
        return "statisticsrewards reload";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        plugin.getConfigFile().load();
        plugin.getConfigManager().reload();
        plugin.getLangFile().load();
        plugin.getLangManager().reload();
        plugin.getRewardsFile().reload();
        plugin.getRewardsManager().reload();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(plugin.getLangManager().getLangValue(LangOption.RELOAD, player));
        }
        Bukkit.getLogger().info("[StatisticsRewards] Configuration files reloaded successfully!");
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        return null;
    }
}
