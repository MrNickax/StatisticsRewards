package me.nickax.statisticsrewards.commands;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.commands.plugin_commands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private final List<CommandBuilder> player_commands = new ArrayList<>();
    private final List<CommandBuilder> console_commands = new ArrayList<>();
    private final StatisticsRewards plugin = StatisticsRewards.getPlugin(StatisticsRewards.class);

    public CommandManager() {
        // Player Commands:
        player_commands.add(new HelpCommand());
        player_commands.add(new ReloadCommand());
        player_commands.add(new SetStatisticCommand());
        player_commands.add(new AddStatisticCommand());
        player_commands.add(new GetStatisticCommand());
        // Console Commands:
        console_commands.add(new HelpCommand());
        console_commands.add(new ReloadCommand());
        console_commands.add(new AddStatisticCommand());
        console_commands.add(new SetStatisticCommand());
        console_commands.add(new GetStatisticCommand());
        //
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 0) {
                if (args.length > 1) {
                    for (CommandBuilder player_command : player_commands) {
                        if (player_command.syntax() != null) {
                            if (args[0].equalsIgnoreCase(player_command.command()) && args[1].equalsIgnoreCase(player_command.syntax())) {
                                try {
                                    player_command.execute(p, args);
                                    return true;
                                } catch (Exception e) {
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lUsage: &a" + player_command.usage()) + "!");
                                    return true;
                                }
                            }
                        }
                    }
                    int c = 0;
                    for (CommandBuilder player_command : player_commands) {
                        if (player_command.permission() != null) {
                            if (p.hasPermission(player_command.permission())) {
                                c++;
                            }
                        } else {
                            c++;
                        }
                    }
                    if (c > 0) {
                        plugin.getMessageManager().unknownCommand(p);
                    } else {
                        plugin.getMessageManager().noPermission(p);
                    }
                    return true;
                }
                for (CommandBuilder player_command : player_commands) {
                    if (player_command.syntax() == null) {
                        if (args[0].equalsIgnoreCase(player_command.command())) {
                            player_command.execute(p, args);
                            return true;
                        }
                    }
                }
                int c = 0;
                for (CommandBuilder player_command : player_commands) {
                    if (player_command.permission() != null) {
                        if (p.hasPermission(player_command.permission())) {
                            c++;
                        }
                    } else {
                        c++;
                    }
                }
                if (c > 0) {
                    plugin.getMessageManager().unknownCommand(p);
                } else {
                    plugin.getMessageManager().noPermission(p);
                }
                return true;
            } else {
                int c = 0;
                for (CommandBuilder player_command : player_commands) {
                    if (player_command.permission() != null) {
                        if (p.hasPermission(player_command.permission())) {
                            c++;
                        }
                    } else {
                        c++;
                    }
                }
                if (c > 0) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8===== &6&lStatisticsRewards &8| &eCommand List &f1/1 &8====="));
                    p.sendMessage("");
                }
                for (CommandBuilder player_command : player_commands) {
                    if (player_command.permission() != null) {
                        if (p.hasPermission(player_command.permission())) {
                            p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + player_command.usage() + " " + ChatColor.GRAY + player_command.description());
                        }
                    } else {
                        p.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + player_command.usage() + " " + ChatColor.GRAY + player_command.description());
                    }
                }
                if (c > 0) {
                    p.sendMessage("");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8--- &eDisplaying &6" + c + "&e commands &8---"));
                } else {
                    plugin.getMessageManager().noPermission(p);
                }
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length > 0) {
                if (args.length > 1) {
                    for (CommandBuilder console_command : console_commands) {
                        if (console_command.syntax() != null) {
                            if (args[0].equalsIgnoreCase(console_command.command()) && args[1].equalsIgnoreCase(console_command.syntax())) {
                                try {
                                    console_command.execute(sender, args);
                                    return true;
                                } catch (Exception e) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lUsage: &a" + console_command.usage()) + "!");
                                    return true;
                                }
                            }
                        }
                    }
                    plugin.getMessageManager().unknownCommand(sender);
                    return true;
                }
                for (CommandBuilder player_command : player_commands) {
                    if (player_command.syntax() == null) {
                        if (args[0].equalsIgnoreCase(player_command.command())) {
                            player_command.execute(sender, args);
                            return true;
                        }
                    }
                }
                plugin.getMessageManager().unknownCommand(sender);
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8===== &6&lStatisticsRewards &8| &eCommand List &f1/1 &8====="));
                sender.sendMessage("");
                for (CommandBuilder console_command : console_commands) {
                    sender.sendMessage(ChatColor.DARK_GRAY + "- " + ChatColor.GREEN + console_command.usage() + " " + ChatColor.GRAY + console_command.description());
                }
                sender.sendMessage("");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8--- &eDisplaying &6" + console_commands.size() + "&e commands &8---"));
            }
        }
        return false;
    }

    public List<CommandBuilder> getPlayerCommands() {
        return player_commands;
    }

    public List<CommandBuilder> getConsoleCommands() {
        return console_commands;
    }
}
