package me.nickax.statisticsrewards.rewards;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.rewards.object.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RewardManager {

    private final StatisticsRewards plugin;
    private final List<Reward> rewardList = new ArrayList<>();
    private Statistic statistic;
    private Material material;
    private EntityType entityType;
    private int amount;
    private String unchecked;

    public RewardManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void load() {
        Long s = System.currentTimeMillis();
        ConfigurationSection rewards = plugin.getConfigManager().config().getConfigurationSection("rewards");
        if (rewardList.size() > 0) {
            rewardList.clear();
        }
        if (rewards != null) {
            rewards.getKeys(false).forEach(reward -> {
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".statistic")) {
                    unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".statistic");
                    if (unchecked == null) return;
                    if (plugin.getCheckers().isStatistic(unchecked)) {
                        statistic = Statistic.valueOf(unchecked.toUpperCase());
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Statistic -> (" + unchecked.toUpperCase() + ") in the reward: " + reward + " is not valid!");
                        return;
                    }
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Statistic is not defined in the reward: " + reward + "!");
                    return;
                }
                Statistic.Type type = statistic.getType();
                if (type.equals(Statistic.Type.BLOCK) || type.equals(Statistic.Type.ITEM)) {
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".material")) {
                        unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".material");
                        if (unchecked == null) return;
                        if (plugin.getCheckers().isMaterial(unchecked)) {
                            material = Material.valueOf(unchecked.toUpperCase());
                        } else {
                            Bukkit.getLogger().warning("[StatisticsRewards] Material -> (" + unchecked.toUpperCase() + ") in the reward: " + reward + " is not valid!");
                            return;
                        }
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Material is not defined in the reward: " + reward + "!");
                        return;
                    }
                }
                if (type.equals(Statistic.Type.ENTITY)) {
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".entity")) {
                        unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".entity");
                        if (unchecked == null) return;
                        if (plugin.getCheckers().isEntity(unchecked)) {
                            entityType = EntityType.valueOf(unchecked.toUpperCase());
                        } else {
                            Bukkit.getLogger().warning("[StatisticsRewards] Entity -> (" + unchecked.toUpperCase() + ") in the reward: " + reward + " is not valid!");
                            return;
                        }
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Entity is not defined in the reward: " + reward + "!");
                        return;
                    }
                }
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".amount")) {
                    unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".amount");
                    if (unchecked == null) return;
                    if (plugin.getCheckers().isInt(unchecked)) {
                        amount = Integer.parseInt(unchecked);
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Amount -> (" + unchecked + ") in the reward: " + reward + " is not valid!");
                        return;
                    }
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Amount is not defined in the reward: " + reward + "!");
                    return;
                }
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".play-sound")) {
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".play-sound" + ".sound")) {
                        unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".play-sound" + ".sound");
                        if (unchecked == null) return;
                        if (!plugin.getCheckers().isSound(unchecked)) {
                            Bukkit.getLogger().warning("[StatisticsRewards] Sound -> (" + unchecked.toUpperCase() + ") in the reward: " + reward + " is not valid!");
                            return;
                        }
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Sound is not defined in the reward: " + reward + "!");
                        return;
                    }
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".play-sound" + ".pitch")) {
                        unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".play-sound" + ".pitch");
                        if (unchecked == null) return;
                        if (!plugin.getCheckers().isInt(unchecked)) {
                            Bukkit.getLogger().warning("[StatisticsRewards] Pitch -> (" + unchecked + ") in the reward: " + reward + " is not valid!");
                            return;
                        }
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Pitch is not defined in the reward: " + reward + "!");
                        return;
                    }
                }
                if (type.equals(Statistic.Type.BLOCK)) {
                    if (material.isBlock()) {
                        rewardList.add(new Reward(reward, statistic, material, null, amount));
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Material -> (" + unchecked + ") in the reward: " + reward + " is not a valid block!");
                    }
                } else if (type.equals(Statistic.Type.ITEM)) {
                    if (material.isItem()) {
                        rewardList.add(new Reward(reward, statistic, material, null, amount));
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Material -> (" + unchecked + ") in the reward: " + reward + " is not a valid item!");
                    }
                } else if (type.equals(Statistic.Type.ENTITY)) {
                    rewardList.add(new Reward(reward, statistic, null, entityType, amount));
                } else if (type.equals(Statistic.Type.UNTYPED)) {
                    rewardList.add(new Reward(reward, statistic, null, null, amount));
                }
            });
            order(rewardList);
            Long e = System.currentTimeMillis();
            if (rewardList.size() > 0) {
                if (rewardList.size() > 1) {
                    Bukkit.getLogger().info("[StatisticsRewards] Loaded " + rewardList.size() + " rewards successfully in " + (e-s) + "ms!");
                } else {
                    Bukkit.getLogger().info("[StatisticsRewards] Loaded " + rewardList.size() + " reward successfully in " + (e-s) + "ms!");
                }
            }
        }
    }

    private void order(List<Reward> rewardList) {
        Comparator<Reward> materialRewardComparator = Comparator.comparingInt(Reward::getAmount);
        rewardList.sort(materialRewardComparator);
    }

    public List<Reward> getRewardList() {
        return rewardList;
    }
}
