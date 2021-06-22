package me.nickax.statisticsrewards.commands.plugin_commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.commands.CommandBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Locale;

public class AddStatisticCommand extends CommandBuilder {

    private final StatisticsRewards plugin = StatisticsRewards.getPlugin(StatisticsRewards.class);

    @Override
    public String command() {
        return "stat";
    }

    @Override
    public String syntax() {
        return "add";
    }

    @Override
    public String permission() {
        return "statisticsrewards.add";
    }

    @Override
    public String description() {
        return "- add statistics to a player.";
    }

    @Override
    public String usage() {
        return "/statisticsrewards stat add " + ChatColor.DARK_GREEN + "<player> <statistic> " + ChatColor.DARK_AQUA + "<entity/material>" + ChatColor.DARK_GREEN + " <amount>";
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
        if (args.length == 6) {
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                plugin.getMessageManager().playerNotFound(sender, StringUtils.capitalize(args[2].toLowerCase()));
                return;
            }
            if (!plugin.getCheckers().isStatistic(args[3].toUpperCase())) {
                plugin.getMessageManager().statisticNotFound(sender, StringUtils.capitalize(args[3].toLowerCase()));
                return;
            }
            Statistic statistic = Statistic.valueOf(args[3].toUpperCase());
            Statistic.Type type = statistic.getType();
            if (type.equals(Statistic.Type.BLOCK)) {
                if (!plugin.getCheckers().isMaterial(args[4])) {
                    plugin.getMessageManager().blockNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                    return;
                } else {
                    Material material = Material.valueOf(args[4].toUpperCase());
                    if (!material.isBlock()) {
                        plugin.getMessageManager().blockNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                        return;
                    }
                }
            } else if (type.equals(Statistic.Type.ENTITY)) {
                if (!plugin.getCheckers().isEntity(args[4].toUpperCase())) {
                    plugin.getMessageManager().entityNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                    return;
                } else {
                    EntityType entityType = EntityType.valueOf(args[4].toUpperCase());
                    if (!entityType.isAlive()) {
                        plugin.getMessageManager().entityNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                        return;
                    }
                }
            } else if (type.equals(Statistic.Type.ITEM)) {
                if (!plugin.getCheckers().isMaterial(args[4])) {
                    plugin.getMessageManager().itemNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                    return;
                } else {
                    Material material = Material.valueOf(args[4].toUpperCase());
                    if (!material.isItem()) {
                        plugin.getMessageManager().itemNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                        return;
                    }
                }
            }
            if (!plugin.getCheckers().isInt(args[5])) {
                plugin.getMessageManager().notValidAmount(sender, args[5]);
                return;
            }
            if (type.equals(Statistic.Type.BLOCK) || type.equals(Statistic.Type.ITEM)) {
                target.setStatistic(Statistic.valueOf(args[3].toUpperCase()), Material.valueOf(args[4].toUpperCase()), target.getStatistic(Statistic.valueOf(args[3].toUpperCase()), Material.valueOf(args[4].toUpperCase())) + Integer.parseInt(args[5]));
                plugin.getMessageManager().addStatistic(sender, target.getName(), target.getStatistic(Statistic.valueOf(args[3].toUpperCase()), Material.valueOf(args[4].toUpperCase(Locale.ROOT))), StringUtils.capitalize(args[3].toLowerCase()), StringUtils.capitalize(args[4].toLowerCase()));
            } else if (type.equals(Statistic.Type.ENTITY)) {
                target.setStatistic(Statistic.valueOf(args[3].toUpperCase()), EntityType.valueOf(args[4].toUpperCase()), target.getStatistic(Statistic.valueOf(args[3].toUpperCase()), EntityType.valueOf(args[4].toUpperCase())) + Integer.parseInt(args[5]));
                plugin.getMessageManager().addStatistic(sender, target.getName(), target.getStatistic(Statistic.valueOf(args[3].toUpperCase()), EntityType.valueOf(args[4].toUpperCase(Locale.ROOT))), StringUtils.capitalize(args[3].toLowerCase()), StringUtils.capitalize(args[4].toLowerCase()));
            }
        } else if (args.length == 5) {
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                plugin.getMessageManager().playerNotFound(sender, StringUtils.capitalize(args[2].toLowerCase()));
                return;
            }
            if (!plugin.getCheckers().isStatistic(args[3].toUpperCase())) {
                plugin.getMessageManager().statisticNotFound(sender, StringUtils.capitalize(args[3].toLowerCase()));
                return;
            }
            Statistic statistic = Statistic.valueOf(args[3].toUpperCase());
            Statistic.Type type = statistic.getType();
            if (type.equals(Statistic.Type.UNTYPED)) {
                if (!plugin.getCheckers().isInt(args[4])) {
                    plugin.getMessageManager().notValidAmount(sender, args[4]);
                    return;
                }
                target.setStatistic(Statistic.valueOf(args[3].toUpperCase()), target.getStatistic(Statistic.valueOf(args[3].toUpperCase())) + Integer.parseInt(args[4]));
                plugin.getMessageManager().addUntypedStatistic(sender, target.getName(), target.getStatistic(Statistic.valueOf(args[3].toUpperCase())), StringUtils.capitalize(args[3].toLowerCase()));
            }
        }
    }
}
