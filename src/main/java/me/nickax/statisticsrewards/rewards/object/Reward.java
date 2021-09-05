package me.nickax.statisticsrewards.rewards.object;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class Reward {

    private final String name;
    private final Statistic statistic;
    private final Material material;
    private final EntityType entityType;
    private final int amount;

    public Reward(String name, Statistic statistic, Material material, EntityType entityType, int amount) {
        this.name = name;
        this.statistic = statistic;
        this.material = material;
        this.entityType = entityType;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Material getMaterial() {
        return material;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getAmount() {
        return amount;
    }
}
