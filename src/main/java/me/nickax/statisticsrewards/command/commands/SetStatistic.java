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

public class SetStatistic extends CommandL {

    private final StatisticsRewards plugin;

    public SetStatistic(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList("stat");
    }

    @Override
    public String subCommand() {
        return "set";
    }

    @Override
    public String permission() {
        return "statisticsrewards.stat.set";
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
        return 6;
    }

    @Override
    public String description() {
        return "set a amount to a player statistic.";
    }

    @Override
    public String usage() {
        return "statisticsrewards stat set <player> <statistic> <entity/material> <amount>";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 2 && args.length <= 4) {
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.USAGE, plugin.getPlayerUtil().getPlayer(sender)).replace("%usage%", usage()));
            return;
        }

        if (args.length < 5) return;

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
            if (args.length == 5) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.USAGE, plugin.getPlayerUtil().getPlayer(sender)).replace("%usage%", usage()));
                return;
            }

            Material material;

            if (!plugin.getCheckerUtil().isMaterial(args[4].toUpperCase())) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_MATERIAL, plugin.getPlayerUtil().getPlayer(sender)).replace("%material%", StringUtils.capitalize(args[4].toLowerCase())));
                return;
            } else {
                material = Material.valueOf(args[4].toUpperCase());
            }

            int amount;

            if (!plugin.getCheckerUtil().isInteger(args[5])) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_NUMBER, plugin.getPlayerUtil().getPlayer(sender)).replace("%number%", args[5]));
                return;
            } else {
                amount = Integer.parseInt(args[5]);
                if (amount < 0) {
                    sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_NUMBER, plugin.getPlayerUtil().getPlayer(sender)).replace("%number%", args[4]));
                    return;
                }
            }

            player.setStatistic(statistic, material, amount);
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.STATISTIC_SET, plugin.getPlayerUtil().getPlayer(sender)).replace("%player%", player.getName()).replace("%statistic%", StringUtils.capitalize(statistic.name().toLowerCase())).replace("%material/entity%", StringUtils.capitalize(material.name().toLowerCase())).replace("%amount%", String.valueOf(player.getStatistic(statistic, material))));
        } else if (statistic.getType().equals(Statistic.Type.ENTITY)) {
            if (args.length == 5) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.USAGE, plugin.getPlayerUtil().getPlayer(sender)).replace("%usage%", usage()));
                return;
            }

            EntityType entityType;

            if (!plugin.getCheckerUtil().isEntity(args[4].toUpperCase())) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_ENTITY, plugin.getPlayerUtil().getPlayer(sender)).replace("%entity%", StringUtils.capitalize(args[4].toLowerCase())));
                return;
            } else {
                entityType = EntityType.valueOf(args[4].toUpperCase());
            }

            int amount;

            if (!plugin.getCheckerUtil().isInteger(args[5])) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_NUMBER, plugin.getPlayerUtil().getPlayer(sender)).replace("%number%", args[5]));
                return;
            } else {
                amount = Integer.parseInt(args[5]);
                if (amount < 0) {
                    sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_NUMBER, plugin.getPlayerUtil().getPlayer(sender)).replace("%number%", args[4]));
                    return;
                }
            }

            player.setStatistic(statistic, entityType, amount);
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.STATISTIC_SET, plugin.getPlayerUtil().getPlayer(sender)).replace("%player%", player.getName()).replace("%statistic%", StringUtils.capitalize(statistic.name().toLowerCase())).replace("%material/entity%", StringUtils.capitalize(entityType.name().toLowerCase())).replace("%amount%", String.valueOf(player.getStatistic(statistic, entityType))));
        } else if (statistic.getType().equals(Statistic.Type.UNTYPED)) {
            int amount;

            if (!plugin.getCheckerUtil().isInteger(args[4])) {
                sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_NUMBER, plugin.getPlayerUtil().getPlayer(sender)).replace("%number%", args[4]));
                return;
            } else {
                amount = Integer.parseInt(args[4]);
                if (amount < 0) {
                    sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.NOT_VALID_NUMBER, plugin.getPlayerUtil().getPlayer(sender)).replace("%number%", args[4]));
                    return;
                }
            }

            player.setStatistic(statistic, amount);
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.STATISTIC_SET_UNTYPED, plugin.getPlayerUtil().getPlayer(sender)).replace("%player%", player.getName()).replace("%statistic%", StringUtils.capitalize(statistic.name().toLowerCase())).replace("%amount%", String.valueOf(player.getStatistic(statistic))));
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
                } else if (statistic.getType().equals(Statistic.Type.UNTYPED)) {
                    arguments.add("1");
                }
            }
            for (String argument : arguments) {
                if (argument.toLowerCase().startsWith(args[4].toLowerCase())) {
                    complete.add(argument);
                }
            }
        }

        if (args.length == 6) {
            Statistic statistic;
            if (plugin.getCheckerUtil().isStatistic(args[3].toUpperCase())) {
                statistic = Statistic.valueOf(args[3].toUpperCase());
                if (statistic.getType().equals(Statistic.Type.ITEM) || statistic.getType().equals(Statistic.Type.BLOCK) || statistic.getType().equals(Statistic.Type.ENTITY)) {
                    arguments.add("1");
                }
            }
            for (String argument : arguments) {
                if (argument.toLowerCase().startsWith(args[5].toLowerCase())) {
                    complete.add(argument);
                }
            }
        }

        return complete;
    }
}
