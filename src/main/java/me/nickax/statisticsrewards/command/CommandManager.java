package me.nickax.statisticsrewards.command;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.command.commands.*;
import me.nickax.statisticsrewards.command.enums.CommandType;
import me.nickax.statisticsrewards.command.object.CommandL;
import me.nickax.statisticsrewards.lang.enums.LangOption;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final StatisticsRewards plugin;

    private final List<CommandL> commandLS;

    public CommandManager(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.commandLS = new ArrayList<>();

        this.commandLS.add(new Reload(plugin));
        this.commandLS.add(new GetStatistic(plugin));
        this.commandLS.add(new SetStatistic(plugin));
        this.commandLS.add(new AddStatistic(plugin));
        this.commandLS.add(new SetCollected(plugin));
        this.commandLS.add(new SetUnCollected(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            for (CommandL commandL : commandLS) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (commandL.commandType().equals(CommandType.PLAYER) || commandL.commandType().equals(CommandType.ALL)) {
                        if (args.length >= commandL.minArguments() && args.length <= commandL.maxArguments()) {
                            if (commandL.aliases().stream().filter(alias -> alias.equalsIgnoreCase(args[0].toLowerCase())).findFirst().orElse(null) != null) {
                                if (commandL.subCommand() != null) {
                                    if (args[1].equalsIgnoreCase(commandL.subCommand())) {
                                        if (commandL.permission() != null) {
                                            if (!player.hasPermission(commandL.permission())) {
                                                player.sendMessage(plugin.getLangManager().getLangValue(LangOption.NO_PERMISSION, player));
                                                return true;
                                            }
                                        }
                                        commandL.execute(player, args);
                                        return true;
                                    }
                                } else {
                                    if (commandL.permission() != null) {
                                        if (!player.hasPermission(commandL.permission())) {
                                            player.sendMessage(plugin.getLangManager().getLangValue(LangOption.NO_PERMISSION, player));
                                            return true;
                                        }
                                    }
                                    commandL.execute(player, args);
                                    return true;
                                }
                            }
                        }
                    }
                } else if (sender instanceof ConsoleCommandSender) {
                    if (commandL.commandType().equals(CommandType.CONSOLE) || commandL.commandType().equals(CommandType.ALL)) {
                        if (args.length >= commandL.minArguments() && args.length <= commandL.maxArguments()) {
                            if (commandL.aliases().stream().filter(alias -> alias.equalsIgnoreCase(args[0].toLowerCase())).findFirst().orElse(null) != null) {
                                if (commandL.subCommand() != null) {
                                    if (args[1].equalsIgnoreCase(commandL.subCommand())) {
                                        commandL.execute(sender, args);
                                        return true;
                                    }
                                } else {
                                    commandL.execute(sender, args);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            sender.sendMessage(plugin.getLangManager().getLangValue(LangOption.UNKNOWN_COMMAND, plugin.getPlayerUtil().getPlayer(sender)));
        } else {
            sender.sendMessage(plugin.getTextUtil().color("&8─ &6&lStatisticsRewards &8» Showing: &7General Help &8─"));
            sender.sendMessage("");
            for (CommandL commandL : commandLS) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (commandL.commandType().equals(CommandType.PLAYER) || commandL.commandType().equals(CommandType.ALL)) {
                        player.sendMessage(plugin.getTextUtil().color("&a/" + commandL.usage() + " &8- &e" + commandL.description()));
                    }
                } else if (sender instanceof ConsoleCommandSender) {
                    if (commandL.commandType().equals(CommandType.CONSOLE) || commandL.commandType().equals(CommandType.ALL)) {
                        sender.sendMessage(plugin.getTextUtil().color("&a/" + commandL.usage() + " &8- &e" + commandL.description()));
                    }
                }
            }
            sender.sendMessage("");
            sender.sendMessage(plugin.getTextUtil().color("&8- &7Displaying &6" + getCommandCount(plugin.getPlayerUtil().getPlayer(sender)) + "&7 commands &8| &7Page &f1/1&7 &8-"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return null;
        Player player = (Player) sender;
        if (args.length == 1) {
            List<String> complete = new ArrayList<>();
            for (CommandL commandL : commandLS) {
                for (String alias : commandL.aliases()) {
                    if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && player.hasPermission(commandL.permission())) {
                        complete.add(alias);
                    }
                }
            }
            return complete;
        } else if (args.length > 1) {
            List<String> complete = new ArrayList<>();
            for (CommandL commandL : commandLS) {
                if (commandL.subCommand() != null) {
                    if (commandL.aliases().stream().filter(alias -> alias.equalsIgnoreCase(args[0].toLowerCase())).findFirst().orElse(null) != null && commandL.subCommand().toLowerCase().startsWith(args[1].toLowerCase()) && player.hasPermission(commandL.permission()) && args.length == commandL.minArguments()) {
                        complete.add(commandL.subCommand());
                    }
                    if (args.length > commandL.minArguments() && args.length <= commandL.maxArguments()) {
                        if (commandL.aliases().stream().filter(alias -> alias.equalsIgnoreCase(args[0].toLowerCase())).findFirst().orElse(null) != null && commandL.subCommand().equalsIgnoreCase(args[1]) && player.hasPermission(commandL.permission())) {
                            if (commandL.tabComplete(sender, command, label, args) != null) {
                                complete.addAll(commandL.tabComplete(sender, command, label, args));
                            }
                        }
                    }
                } else if (commandL.subCommand() == null) {
                    if (args.length > commandL.minArguments() && args.length <= commandL.maxArguments()) {
                        if (commandL.aliases().stream().filter(alias -> alias.equalsIgnoreCase(args[0].toLowerCase())).findFirst().orElse(null) != null && player.hasPermission(commandL.permission())) {
                            if (commandL.tabComplete(sender, command, label, args) != null) {
                                complete.addAll(commandL.tabComplete(sender, command, label, args));
                            }
                        }
                    }
                }
            }
            return complete;
        }
        return null;
    }

    public int getCommandCount(Player player) {
        int i = 0;
        if (player == null) {
            for (CommandL commandL : commandLS) {
                if ((commandL.commandType().equals(CommandType.ALL) || commandL.commandType().equals(CommandType.CONSOLE)) && !commandL.hide()) {
                    i++;
                }
            }
        } else {
            for (CommandL commandL : commandLS) {
                if (player.hasPermission(commandL.permission()) && !commandL.hide()) {
                    i++;
                }
            }
        }
        return i;
    }
}
