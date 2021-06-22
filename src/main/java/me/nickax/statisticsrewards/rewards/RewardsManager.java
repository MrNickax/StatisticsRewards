package me.nickax.statisticsrewards.rewards;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.rewards.format.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class RewardsManager {

    private final StatisticsRewards plugin;
    private final List<Reward> rewardList = new ArrayList<>();
    private String unchecked;
    private Statistic statistic;
    private Material material;
    private EntityType entityType;
    private int amount;
    private boolean sound;
    private boolean message;
    private boolean playerCommand;
    private boolean consoleCommand;

    public RewardsManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void load() {
        Long s = System.currentTimeMillis();
        ConfigurationSection rewards = plugin.getConfigManager().config().getConfigurationSection("rewards");
        if (rewards != null) {
            rewards.getKeys(false).forEach(reward -> {
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".statistic")) {
                    unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".statistic");
                    if (plugin.getCheckers().isStatistic(unchecked)) {
                        statistic = Statistic.valueOf(unchecked.toUpperCase());
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Statistic " + unchecked.toUpperCase() + " in the reward: " + reward + " is not valid!");
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
                        assert unchecked != null;
                        if (plugin.getCheckers().isMaterial(unchecked)) {
                            material = Material.valueOf(unchecked.toUpperCase());
                        } else {
                            Bukkit.getLogger().warning("[StatisticsRewards] Material " + unchecked.toUpperCase() + " in the reward: " + reward + " is not valid!");
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
                        if (plugin.getCheckers().isEntity(unchecked)) {
                            entityType = EntityType.valueOf(unchecked.toUpperCase());
                        } else {
                            Bukkit.getLogger().warning("[StatisticsRewards] Entity " + unchecked.toUpperCase() + " in the reward: " + reward + " is not valid!");
                            return;
                        }
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Entity is not defined in the reward: " + reward + "!");
                        return;
                    }
                }
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".amount")) {
                    unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".amount");
                    if (plugin.getCheckers().isInt(unchecked)) {
                        amount = Integer.parseInt(unchecked);
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Amount " + unchecked + " in the reward: " + reward + " is not valid!");
                        return;
                    }
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Amount is not defined in the reward: " + reward + "!");
                    return;
                }
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".messages")) {
                    message = true;
                }
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".commands")) {
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".commands" + ".player")) {
                        playerCommand = true;
                    }
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".commands" + ".console")) {
                        consoleCommand = true;
                    }
                }
                if (plugin.getConfigManager().config().contains("rewards." + reward + ".play-sound")) {
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".play-sound" + ".sound")) {
                        unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".play-sound" + ".sound");
                        if (!plugin.getCheckers().isSound(unchecked)) {
                            Bukkit.getLogger().warning("[StatisticsRewards] Sound " + unchecked.toUpperCase() + " in the reward: " + reward + " is not valid!");
                            return;
                        }
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Sound is not defined in the reward: " + reward + "!");
                        return;
                    }
                    if (plugin.getConfigManager().config().contains("rewards." + reward + ".play-sound" + ".pitch")) {
                        unchecked = plugin.getConfigManager().config().getString("rewards." + reward + ".play-sound" + ".pitch");
                        if (plugin.getCheckers().isInt(unchecked)) {
                            sound = true;
                        } else {
                            Bukkit.getLogger().warning("[StatisticsRewards] Pitch " + unchecked + " in the reward: " + reward + " is not valid!");
                            return;
                        }
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Pitch is not defined in the reward: " + reward + "!");
                        return;
                    }
                }
                if (type.equals(Statistic.Type.BLOCK)) {
                    if (material.isBlock()) {
                        rewardList.add(new Reward(reward, statistic, null, material, amount, sound, message, playerCommand, consoleCommand));
                    }
                } else if (type.equals(Statistic.Type.ITEM)) {
                    if (material.isItem()) {
                        rewardList.add(new Reward(reward, statistic, null, material, amount, sound, message, playerCommand, consoleCommand));
                    }
                } else if (type.equals(Statistic.Type.ENTITY)) {
                    rewardList.add(new Reward(reward, statistic, entityType, null, amount, sound, message, playerCommand, consoleCommand));
                } else if (type.equals(Statistic.Type.UNTYPED)) {
                    rewardList.add(new Reward(reward, statistic, null, null, amount, sound, message, playerCommand, consoleCommand));
                }
            });
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
    public void reload() {
        rewardList.clear();
        load();
    }

    public List<Reward> getRewardList() {
        return rewardList;
    }
}