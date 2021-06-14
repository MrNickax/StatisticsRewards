package me.nickax.statisticsrewards.statistics;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardsManager implements Listener {

    private final StatisticsRewards plugin;
    private final Map<String, Statistic.Type> loadedRewards = new HashMap<>();
    private String invalid;
    private Statistic statistic;
    private Statistic.Type type;
    private Material material;
    private EntityType entity;
    private Integer loaded = 0;

    public RewardsManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void load() {
        Long s = System.currentTimeMillis();
        if (plugin.getConfig().contains("rewards")) {
            getLoadedRewards().clear();
            loaded = 0;
            plugin.getConfig().getConfigurationSection("rewards").getKeys(false).forEach(key -> {
                if (plugin.getConfig().contains("rewards." + key + ".statistic")) {
                    if (plugin.getCheckers().isStatistic(plugin.getConfig().getString("rewards." + key + ".statistic"))) {
                        statistic = Statistic.valueOf(plugin.getConfig().getString("rewards." + key + ".statistic"));
                    } else {
                        invalid = plugin.getConfig().getString("rewards." + key + ".statistic");
                        Bukkit.getLogger().severe("[StatisticsRewards] Statistic " + invalid + " in the reward: " + key + " is not a valid statistic!");
                        return;
                    }
                } else {
                    Bukkit.getLogger().severe("[StatisticsRewards] Statistic is not defined in the reward: " + key + "!");
                    return;
                }
                type = statistic.getType();
                if (type.equals(Statistic.Type.BLOCK)) {
                    if (plugin.getConfig().contains("rewards." + key + ".material")) {
                        if (plugin.getCheckers().isMaterial(plugin.getConfig().getString("rewards." + key + ".material"))) {
                            material = Material.valueOf(plugin.getConfig().getString("rewards." + key + ".material").toUpperCase());
                            if (plugin.getCheckers().isBlock(material)) {
                                loadedRewards.put(key, type);
                                loaded = loaded + 1;
                            } else {
                                String invalid = plugin.getConfig().getString("rewards." + key + ".material");
                                Bukkit.getLogger().severe("[StatisticsRewards] Material " + invalid + " in the reward: " + key + " is not a valid block!");
                            }
                        } else {
                            String invalid = plugin.getConfig().getString("rewards." + key + ".material");
                            Bukkit.getLogger().severe("[StatisticsRewards] Material " + invalid + " in the reward: " + key + " is not a valid block!");
                        }
                    } else {
                        Bukkit.getLogger().severe("[StatisticsRewards] Material is not defined in the reward: " + key + "!");
                    }
                } else if (type.equals(Statistic.Type.ITEM)) {
                    if (plugin.getConfig().contains("rewards." + key + ".material")) {
                        if (plugin.getCheckers().isMaterial(plugin.getConfig().getString("rewards." + key + ".material"))) {
                            material = Material.valueOf(plugin.getConfig().getString("rewards." + key + ".material").toUpperCase());
                            if (plugin.getCheckers().isItem(material)) {
                                loadedRewards.put(key, type);
                                loaded = loaded + 1;
                            } else {
                                String invalid = plugin.getConfig().getString("rewards." + key + ".material");
                                Bukkit.getLogger().severe("[StatisticsRewards] Material " + invalid + " in the reward: " + key + " is not a valid item!");
                            }
                        } else {
                            String invalid = plugin.getConfig().getString("rewards." + key + ".material");
                            Bukkit.getLogger().severe("[StatisticsRewards] Material " + invalid + " in the reward: " + key + " is not a valid item!");
                        }
                    } else {
                        Bukkit.getLogger().severe("[StatisticsRewards] Material is not defined in the reward: " + key + "!");
                    }
                } else if (type.equals(Statistic.Type.ENTITY)) {
                    if (plugin.getConfig().contains("rewards." + key + ".entity")) {
                        if (plugin.getCheckers().isEntity(plugin.getConfig().getString("rewards." + key + ".entity"))) {
                            entity = EntityType.valueOf(plugin.getConfig().getString("rewards." + key + ".entity").toUpperCase());
                            if (entity.isAlive()) {
                                loadedRewards.put(key, type);
                                loaded = loaded + 1;
                            } else {
                                String invalid = plugin.getConfig().getString("rewards." + key + ".entity");
                                Bukkit.getLogger().severe("[StatisticsRewards] Entity " + invalid + " in the reward: " + key + " is not a valid entity!");
                            }
                        } else {
                            String invalid = plugin.getConfig().getString("rewards." + key + ".entity");
                            Bukkit.getLogger().severe("[StatisticsRewards] Entity " + invalid + " in the reward: " + key + " is not a valid entity!");
                        }
                    } else {
                        Bukkit.getLogger().severe("[StatisticsRewards] Entity is not defined in the reward: " + key + "!");
                    }
                } else if (type.equals(Statistic.Type.UNTYPED)) {
                    loadedRewards.put(key, type);
                    loaded = loaded + 1;
                }
            });
            Long e = System.currentTimeMillis();
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + loaded + " rewards successfully in " + (e-s) + "ms.");
        }
    }
    public Map<String, Statistic.Type> getLoadedRewards() {
        return loadedRewards;
    }
}
