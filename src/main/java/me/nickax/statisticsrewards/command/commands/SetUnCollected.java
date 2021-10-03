package me.nickax.statisticsrewards.command.commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.command.enums.CommandType;
import me.nickax.statisticsrewards.command.object.CommandL;
import me.nickax.statisticsrewards.data.object.PlayerData;
import me.nickax.statisticsrewards.lang.enums.LangOption;
import me.nickax.statisticsrewards.rewards.object.Reward;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetUnCollected extends CommandL {

    private final StatisticsRewards plugin;

    public SetUnCollected(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("set");
    }

    @Override
    public String subCommand() {
        return "uncollected";
    }

    @Override
    public String permission() {
        return "statisticsrewards.set.uncollected";
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
        return 2;
    }

    @Override
    public int maxArguments() {
        return 5;
    }

    @Override
    public String description() {
        return "set a reward uncollected to a player";
    }

    @Override
    public String usage() {
        return "statisticsrewards set uncollected <player> <reward>";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 2 && args.length <= 3) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.USAGE, plugin.getPlayerUtil().getPlayer(sender)).replace("%usage%", usage()));
            return;
        }

        if (args.length < 4) return;

        Player player = Bukkit.getPlayer(args[2]);

        if (player == null) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_PLAYER, plugin.getPlayerUtil().getPlayer(sender)).replace("%player%", StringUtils.capitalize(args[2].toLowerCase())));
            return;
        }

        if (plugin.getRewardsManager().getRewards().isEmpty()) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.REWARD_NOT_CONFIGURED, plugin.getPlayerUtil().getPlayer(sender)));
            return;
        }

        Reward reward = plugin.getRewardsManager().getReward(args[3]);

        if (reward == null) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.REWARD_NOT_FOUND, plugin.getPlayerUtil().getPlayer(sender)));
            return;
        }

        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        if (!playerData.getReceivedRewards().contains(reward.getName())) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.REWARD_NOT_COLLECTED, plugin.getPlayerUtil().getPlayer(sender)).replace("%player%", player.getName()).replace("%reward%", reward.getName()));
            return;
        }

        playerData.removeReceivedReward(reward.getName());
        sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.REWARD_SET_UNCOLLECTED, plugin.getPlayerUtil().getPlayer(sender)).replace("%player%", player.getName()).replace("%reward%", reward.getName()));
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        List<String> complete = new ArrayList<>();
        List<String> arguments = new ArrayList<>();

        if (args.length == 3) {
            Bukkit.getOnlinePlayers().forEach(player -> arguments.add(player.getName()));
            for (String argument : arguments) {
                if (argument.toLowerCase().startsWith(args[2].toLowerCase())) {
                    complete.add(argument);
                }
            }
        }

        if (args.length == 4) {
            Player player = Bukkit.getPlayer(args[2]);
            if (player != null) {
                PlayerData playerData = plugin.getDataManager().getPlayerData(player);
                if (playerData != null) {
                    for (Reward reward : plugin.getRewardsManager().getRewards()) {
                        if (playerData.getReceivedRewards().contains(reward.getName())) {
                            arguments.add(reward.getName());
                        }
                    }
                }
            }
            for (String argument : arguments) {
                if (argument.toLowerCase().startsWith(args[3].toLowerCase())) {
                    complete.add(argument);
                }
            }
        }

        return complete;
    }
}
