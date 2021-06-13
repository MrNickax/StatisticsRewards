package me.nickax.statisticsrewards.util;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class Checkers {

    private final StatisticsRewards plugin;

    public Checkers(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public boolean isMaterial(String material) {
        return Material.matchMaterial(material) != null;
    }

    public boolean isBlock(Material material) {
        return material.isBlock();
    }

    public boolean isItem(Material material) {
        return material.isItem();
    }

    public boolean isEntity(String entity) {
        try {
            EntityType.valueOf(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStatistic(String statistic) {
        try {
            Statistic.valueOf(statistic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
