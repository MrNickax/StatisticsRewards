package me.nickax.statisticsrewards.rewards.format;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class Reward {

    private final String rewardID;
    private final Statistic statistic;
    private final Material material;
    private final EntityType entityType;
    private final int amount;
    private final boolean sound;
    private final boolean message;
    private final boolean playerCommand;
    private final boolean consoleCommand;

    public Reward(String rewardID, Statistic statistic, EntityType entityType, Material material, int amount, boolean sound, boolean message, boolean playerCommand, boolean consoleCommand) {
        this.rewardID = rewardID;
        this.statistic = statistic;
        this.material = material;
        this.amount = amount;
        this.sound = sound;
        this.message = message;
        this.playerCommand = playerCommand;
        this.consoleCommand = consoleCommand;
        this.entityType = entityType;
    }

    public String getRewardID() {
        return rewardID;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isMessage() {
        return message;
    }

    public boolean isPlayerCommand() {
        return playerCommand;
    }

    public boolean isConsoleCommand() {
        return consoleCommand;
    }

    public boolean isSound() {
        return sound;
    }
}
