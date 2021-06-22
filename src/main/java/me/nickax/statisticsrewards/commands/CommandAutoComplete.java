package me.nickax.statisticsrewards.commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.rewards.RewardsManager;
import me.nickax.statisticsrewards.rewards.format.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandAutoComplete implements TabCompleter {

    private final List<String> arguments = new ArrayList<>();
    private final StatisticsRewards plugin = StatisticsRewards.getPlugin(StatisticsRewards.class);

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> complete = new ArrayList<>();
        Player p = (Player) sender;
        if (command.getName().equalsIgnoreCase("statisticsrewards")) {
            if (args.length == 1) {
                arguments.clear();
                if (p.hasPermission("statisticsrewards.add") || p.hasPermission("statisticsrewards.set") || p.hasPermission("statisticsrewards.reload") || p.hasPermission("statisticsrewards.set.collected") || p.hasPermission("statisticsrewards.set.uncollected")) {
                    arguments.add("help");
                }
                if (p.hasPermission("statisticsrewards.reload")) {
                    arguments.add("reload");
                }
                if (p.hasPermission("statisticsrewards.set.collected") || p.hasPermission("statisticsrewards.set.uncollected")) {
                    arguments.add("set");
                }
                if (p.hasPermission("statisticsrewards.add") || p.hasPermission("statisticsrewards.set") || p.hasPermission("statisticsrewards.get")) {
                    arguments.add("stat");
                }
                for (String string : arguments) {
                    if (string.toLowerCase().startsWith(args[0].toLowerCase())) {
                        complete.add(string);
                    }
                }
            } else if (args.length == 2) {
                arguments.clear();
                if (args[0].equalsIgnoreCase("stat")) {
                    if (p.hasPermission("statisticsrewards.add")) {
                        arguments.add("add");
                    }
                    if (p.hasPermission("statisticsrewards.set")) {
                        arguments.add("set");
                    }
                    if (p.hasPermission("statisticsrewards.get")) {
                        arguments.add("get");
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (p.hasPermission("statisticsrewards.set.collected")) {
                        arguments.add("collected");
                    }
                    if (p.hasPermission("statisticsrewards.set.uncollected")) {
                        arguments.add("uncollected");
                    }
                }
                for (String string : arguments) {
                    if (string.toLowerCase().startsWith(args[1].toLowerCase())) {
                        complete.add(string);
                    }
                }
            } else if (args.length == 3) {
                arguments.clear();
                if (args[0].equalsIgnoreCase("stat")) {
                    if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("get")) {
                        if (p.hasPermission("statisticsrewards.add") || p.hasPermission("statisticsrewards.set") || p.hasPermission("statisticsrewards.get")) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                arguments.add(player.getName());
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args[1].equalsIgnoreCase("collected") || args[1].equalsIgnoreCase("uncollected")) {
                        if (p.hasPermission("statisticsrewards.set.collected") || p.hasPermission("statisticsrewards.set.uncollected")) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                arguments.add(player.getName());
                            }
                        }
                    }
                }
                for (String string : arguments) {
                    if (string.toLowerCase().startsWith(args[2].toLowerCase())) {
                        complete.add(string);
                    }
                }
            } else if (args.length == 4) {
                arguments.clear();
                if (args[0].equalsIgnoreCase("stat")) {
                    if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("get")) {
                        if (p.hasPermission("statisticsrewards.add") || p.hasPermission("statisticsrewards.set") || p.hasPermission("statisticsrewards.get")) {
                            for (Statistic statistic : Statistic.values()) {
                                String stat = String.valueOf(statistic).toLowerCase();
                                arguments.add(stat);
                            }
                        }
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (args[1].equalsIgnoreCase("uncollected")) {
                        Player player = Bukkit.getPlayer(args[2]);
                        if (player != null) {
                            DataManager dataManager = plugin.getPlayerData().getPlayer(player);
                            if (dataManager != null) {
                                for (Map.Entry<String, Boolean> reward : dataManager.getCollectedRewards().entrySet()) {
                                    if (reward.getKey() != null) {
                                        arguments.add(reward.getKey());
                                    }
                                }
                            }
                        }
                    } else if (args[1].equalsIgnoreCase("collected")) {
                        Player player = Bukkit.getPlayer(args[2]);
                        if (player != null) {
                            for (Reward reward : plugin.getRewardsManager().getRewardList()) {
                                if (reward.getRewardID() != null) {
                                    arguments.add(reward.getRewardID());
                                }
                            }
                        }
                    }
                }
                for (String string : arguments) {
                    if (string.toLowerCase().startsWith(args[3].toLowerCase())) {
                        complete.add(string);
                    }
                }
            } else if (args.length == 5) {
                arguments.clear();
                if (args[0].equalsIgnoreCase("stat")) {
                    if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("get")) {
                        if (p.hasPermission("statisticsrewards.add") || p.hasPermission("statisticsrewards.set") || p.hasPermission("statisticsrewards.get")) {
                            if (isStatistic(args[3].toUpperCase())) {
                                Statistic.Type type = Statistic.valueOf(args[3].toUpperCase()).getType();
                                if (type.equals(Statistic.Type.BLOCK)) {
                                    for (Material material : Material.values()) {
                                        if (material.isBlock()) {
                                            String block = String.valueOf(material).toLowerCase();
                                            arguments.add(block);
                                        }
                                    }
                                } else if (type.equals(Statistic.Type.ITEM)) {
                                    for (Material material : Material.values()) {
                                        if (material.isItem()) {
                                            String item = String.valueOf(material).toLowerCase();
                                            arguments.add(item);
                                        }
                                    }
                                } else if (type.equals(Statistic.Type.ENTITY)) {
                                    for (EntityType entityType : EntityType.values()) {
                                        if (entityType.isAlive()) {
                                            String entity = String.valueOf(entityType).toLowerCase();
                                            arguments.add(entity);
                                        }
                                    }
                                } else if (type.equals(Statistic.Type.UNTYPED)) {
                                    if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add")) {
                                        arguments.add("1");
                                    }
                                }
                            }
                        }
                    }
                }
                for (String string : arguments) {
                    if (string.toLowerCase().startsWith(args[4].toLowerCase())) {
                        complete.add(string);
                    }
                }
            } else if (args.length == 6) {
                arguments.clear();
                if (args[0].equalsIgnoreCase("stat")) {
                    if (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add")) {
                        if (p.hasPermission("statisticsrewards.add") || p.hasPermission("statisticsrewards.set")) {
                            if (isStatistic(args[3].toUpperCase())) {
                                Statistic.Type type = Statistic.valueOf(args[3].toUpperCase()).getType();
                                if (!type.equals(Statistic.Type.UNTYPED)) {
                                    arguments.add("1");
                                }
                            }
                        }
                    }
                }
                for (String string : arguments) {
                    if (string.toLowerCase().startsWith(args[5].toLowerCase())) {
                        complete.add(string);
                    }
                }
            }
            return complete;
        }
        return null;
    }
    private boolean isStatistic(String statistic) {
        try {
            Statistic.valueOf(statistic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
