package me.nickax.statisticsrewards.command.object;

import me.nickax.statisticsrewards.command.enums.CommandType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CommandL {

    public abstract List<String> aliases();
    public abstract String subCommand();
    public abstract String permission();
    public abstract boolean hide();
    public abstract CommandType commandType();
    public abstract int minArguments();
    public abstract int maxArguments();
    public abstract String description();
    public abstract String usage();

    public abstract void execute(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(CommandSender commandSender, Command command, String label, String[] args);
}
