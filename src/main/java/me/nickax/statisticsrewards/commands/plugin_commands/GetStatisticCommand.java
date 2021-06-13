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

public class GetStatisticCommand extends CommandBuilder {

    private final StatisticsRewards plugin = StatisticsRewards.getPlugin(StatisticsRewards.class);

    @Override
    public String command() {
        return "stat";
    }

    @Override
    public String syntax() {
        return "get";
    }

    @Override
    public String permission() {
        return "statisticsrewards.get";
    }

    @Override
    public String description() {
        return "- show the amount of a player statistic.";
    }

    @Override
    public String usage() {
        return "/statisticsrewards stat get " + ChatColor.DARK_GREEN + "<player> <statistic> " + ChatColor.DARK_AQUA + "<entity/material>";
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
        Player target = Bukkit.getPlayer(args[2]);
        if (target == null) {
            plugin.getMessageManager().playerNotFound(sender, StringUtils.capitalize(args[2].toLowerCase()));
            return;
        }
        if (!isStatistic(args[3].toUpperCase())) {
            plugin.getMessageManager().statisticNotFound(sender, StringUtils.capitalize(args[3].toLowerCase()));
            return;
        }
        Statistic statistic = Statistic.valueOf(args[3].toUpperCase());
        Statistic.Type type = statistic.getType();
        if (args.length == 5) {
            if (type.equals(Statistic.Type.BLOCK)) {
                if (Material.matchMaterial(args[4]) == null) {
                    plugin.getMessageManager().blockNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                    return;
                } else if (Material.matchMaterial(args[4]) != null) {
                    if (!Material.matchMaterial(args[4]).isBlock()) {
                        plugin.getMessageManager().blockNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                        return;
                    }
                }
            } else if (type.equals(Statistic.Type.ENTITY)) {
                if (!isEntity(args[4].toUpperCase())) {
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
                if (Material.matchMaterial(args[4]) == null) {
                    plugin.getMessageManager().itemNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                    return;
                } else if (Material.matchMaterial(args[4]) != null) {
                    if (!Material.matchMaterial(args[4]).isItem()) {
                        plugin.getMessageManager().itemNotFound(sender, StringUtils.capitalize(args[4].toLowerCase()));
                        return;
                    }
                }
            }
            if (type.equals(Statistic.Type.BLOCK) || type.equals(Statistic.Type.ITEM)) {
                Material material = Material.valueOf(args[4].toUpperCase());
                plugin.getMessageManager().getStatistic(sender, target.getName(), target.getStatistic(statistic, material), StringUtils.capitalize(statistic.toString().toLowerCase()), StringUtils.capitalize(material.toString().toLowerCase()));
            } else if (type.equals(Statistic.Type.ENTITY)) {
                EntityType entityType = EntityType.valueOf(args[4].toUpperCase());
                plugin.getMessageManager().getStatistic(sender, target.getName(), target.getStatistic(statistic, entityType), StringUtils.capitalize(statistic.toString().toLowerCase()), StringUtils.capitalize(entityType.toString().toLowerCase()));
            }
        } else if (args.length == 4) {
            if (type.equals(Statistic.Type.UNTYPED)) {
                plugin.getMessageManager().getUntypedStatistic(sender, target.getName(), target.getStatistic(statistic), StringUtils.capitalize(statistic.toString().toLowerCase()));
            }
        }
    }
    private boolean isStatistic(String statistic) {
        try {
            Statistic.valueOf(statistic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private boolean isEntity(String entity) {
        try {
            EntityType.valueOf(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
