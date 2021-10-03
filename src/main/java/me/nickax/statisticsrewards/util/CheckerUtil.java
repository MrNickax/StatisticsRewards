package me.nickax.statisticsrewards.util;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class CheckerUtil {

    private final StatisticsRewards plugin;

    public CheckerUtil(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public boolean isStatistic(String statistic) {
        try {
            Statistic.valueOf(statistic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMaterial(String material) {
        return Material.matchMaterial(material.toUpperCase()) != null;
    }

    public boolean isInteger(String integer) {
        try {
            Integer.parseInt(integer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEntity(String entityType) {
        try {
            EntityType.valueOf(entityType);
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

    public void getVersion(int resourceId, final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }
}
