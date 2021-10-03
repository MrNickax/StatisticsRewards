package me.nickax.statisticsrewards.rewards.object;

import me.nickax.statisticsrewards.command.enums.CommandType;

public class Command {

    private final String command;
    private final CommandType commandType;

    public Command(String command, CommandType commandType) {
        this.command = command;
        this.commandType = commandType;
    }

    public String getCommand() {
        return command;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
