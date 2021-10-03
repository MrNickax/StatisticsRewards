package me.nickax.statisticsrewards.rewards.object;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Reward {

    private final String name;
    private final Statistic statistic;
    private final Integer amount;

    private Material material;
    private EntityType entityType;
    private Sound sound;
    private Integer pitch;

    private List<String> messages;
    private final List<Command> commands;

    public Reward(String name, Statistic statistic, Integer amount) {
        this.name = name;
        this.statistic = statistic;
        this.amount = amount;
        this.messages = new ArrayList<>();
        this.commands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Integer getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Integer getPitch() {
        return pitch;
    }

    public void setPitch(Integer pitch) {
        this.pitch = pitch;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }
}
