package me.nickax.statisticsrewards.util;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

public class Checkers {

    private final StatisticsRewards plugin;

    public Checkers(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public boolean isStatistic(String statistic) {
        try {
            Statistic.valueOf(statistic.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isMaterial(String material) {
        return Material.matchMaterial(material.toUpperCase()) != null;
    }
    public boolean isEntity(String entity) {
        try {
            EntityType.valueOf(entity.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isInt(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isSound(String sound) {
        try {
            Sound.valueOf(sound);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
