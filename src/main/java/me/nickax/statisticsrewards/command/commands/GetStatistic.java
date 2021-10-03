package me.nickax.statisticsrewards.command.commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.command.enums.CommandType;
import me.nickax.statisticsrewards.command.object.CommandL;
import me.nickax.statisticsrewards.lang.enums.LangOption;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetStatistic extends CommandL {

    private final StatisticsRewards plugin;

    public GetStatistic(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("stat");
    }

    @Override
    public String subCommand() {
        return "get";
    }

    @Override
    public String permission() {
        return "statisticsrewards.stat.get";
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
        return "show the amount of a player statistic.";
    }

    @Override
    public String usage() {
        return "statisticsrewards stat get <player> <statistic> <entity/material>";
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

        Statistic statistic;

        if (!plugin.getCheckerUtil().isStatistic(args[3].toUpperCase())) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_STATISTIC, plugin.getPlayerUtil().getPlayer(sender)).replace("%statistic%", StringUtils.capitalize(args[3].toLowerCase())));
            return;
        } else {
            statistic = Statistic.valueOf(args[3].toUpperCase());
        }

        if (statistic.getType().equals(Statistic.Type.ITEM) || statistic.getType().equals(Statistic.Type.BLOCK)) {
            if (args.length == 4) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.USAGE, plugin.getPlayerUtil().getPlayer(sender)).replace("%usage%", usage()));
                return;
            }
            if (plugin.getCheckerUtil().isMaterial(args[4].toUpperCase())) {
                Material material = Material.valueOf(args[4].toUpperCase());
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.STATISTIC_GET, plugin.getPlayerUtil().getPlayer(player)).replace("%player%", player.getName()).replace("%statistic%", StringUtils.capitalize(statistic.name().toLowerCase())).replace("%material/entity%", StringUtils.capitalize(material.name().toLowerCase())).replace("%amount%", String.valueOf(player.getStatistic(statistic, material))));
            } else {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_MATERIAL, plugin.getPlayerUtil().getPlayer(sender)).replace("%material%", StringUtils.capitalize(args[4].toLowerCase())));
            }
        } else if (statistic.getType().equals(Statistic.Type.ENTITY)) {
            if (args.length == 4) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.USAGE, plugin.getPlayerUtil().getPlayer(sender)).replace("%usage%", usage()));
                return;
            }
            if (plugin.getCheckerUtil().isEntity(args[4].toUpperCase())) {
                EntityType entityType = EntityType.valueOf(args[4].toUpperCase());
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.STATISTIC_GET, plugin.getPlayerUtil().getPlayer(player)).replace("%player%", player.getName()).replace("%statistic%", StringUtils.capitalize(statistic.name().toLowerCase())).replace("%material/entity%", StringUtils.capitalize(entityType.name().toLowerCase())).replace("%amount%", String.valueOf(player.getStatistic(statistic, entityType))));
            } else {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_ENTITY, plugin.getPlayerUtil().getPlayer(sender)).replace("%entity%", StringUtils.capitalize(args[4].toLowerCase())));
            }
        } else if (statistic.getType().equals(Statistic.Type.UNTYPED)) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.STATISTIC_GET_UNTYPED, plugin.getPlayerUtil().getPlayer(player)).replace("%player%", player.getName()).replace("%statistic%", StringUtils.capitalize(statistic.name().toLowerCase())).replace("%amount%", String.valueOf(player.getStatistic(statistic))));
        }
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
            for (Statistic statistic : Statistic.values()) {
                arguments.add(StringUtils.capitalize(statistic.name().toLowerCase()));
            }
            for (String argument : arguments) {
                if (argument.toLowerCase().startsWith(args[3].toLowerCase())) {
                    complete.add(argument);
                }
            }
        }

        if (args.length == 5) {
            Statistic statistic;
            if (plugin.getCheckerUtil().isStatistic(args[3].toUpperCase())) {
                statistic = Statistic.valueOf(args[3].toUpperCase());
                if (statistic.getType().equals(Statistic.Type.ITEM) || statistic.getType().equals(Statistic.Type.BLOCK)) {
                    for (Material material : Material.values()) {
                        arguments.add(StringUtils.capitalize(material.name().toLowerCase()));
                    }
                } else if (statistic.getType().equals(Statistic.Type.ENTITY)) {
                    for (EntityType entityType : EntityType.values()) {
                        arguments.add(StringUtils.capitalize(entityType.name().toLowerCase()));
                    }
                }
            }
            for (String argument : arguments) {
                if (argument.toLowerCase().startsWith(args[4].toLowerCase())) {
                    complete.add(argument);
                }
            }
        }

        return complete;
    }
}
