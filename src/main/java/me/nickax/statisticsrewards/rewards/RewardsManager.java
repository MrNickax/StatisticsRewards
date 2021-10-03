package me.nickax.statisticsrewards.rewards;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.command.enums.CommandType;
import me.nickax.statisticsrewards.rewards.object.Command;
import me.nickax.statisticsrewards.rewards.object.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.*;
import java.util.stream.Collectors;

public class RewardsManager {

    private final StatisticsRewards plugin;

    private Set<Reward> rewards;

    public RewardsManager(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.rewards = new HashSet<>();
    }

    public void load() {
        Long start = System.currentTimeMillis();

        FileConfiguration configuration = plugin.getRewardsFile().configuration();
        configuration.getKeys(false).forEach(key -> {

            String textStatistic = configuration.getString(key + ".statistic");
            Statistic statistic;

            if (textStatistic != null) {
                if (plugin.getCheckerUtil().isStatistic(textStatistic.toUpperCase())) {
                    statistic = Statistic.valueOf(textStatistic.toUpperCase());
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Statistic " + textStatistic.toUpperCase() + " in the reward: " + key + " is not valid!");
                    return;
                }
            } else {
                Bukkit.getLogger().warning("[StatisticsRewards] Statistic is not defined in the reward: " + key + "!");
                return;
            }

            String textAmount = configuration.getString(key + ".amount");
            int amount;

            if (textAmount != null) {
                if (plugin.getCheckerUtil().isInteger(textAmount)) {
                    amount = Integer.parseInt(textAmount);
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Amount " + textAmount + " in the reward: " + key + " is not valid!");
                    return;
                }
            } else {
                Bukkit.getLogger().warning("[StatisticsRewards] Amount is not defined in the reward: " + key + "!");
                return;
            }

            Reward reward = new Reward(key, statistic, amount);

            if (statistic.getType().equals(Statistic.Type.BLOCK)) {
                String textMaterial = configuration.getString(key + ".material");
                if (textMaterial != null) {
                    if (plugin.getCheckerUtil().isMaterial(textMaterial.toUpperCase())) {
                        reward.setMaterial(Material.valueOf(textMaterial.toUpperCase()));
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Material " + textMaterial.toUpperCase() + " in the reward: " + key + " is not valid!");
                        return;
                    }
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Material is not defined in the reward: " + key + "!");
                    return;
                }
            } else if (statistic.getType().equals(Statistic.Type.ENTITY)) {
                String textEntity = configuration.getString(key + ".entity");
                if (textEntity != null) {
                    if (plugin.getCheckerUtil().isEntity(textEntity.toUpperCase())) {
                        reward.setEntityType(EntityType.valueOf(textEntity.toUpperCase()));
                    } else {
                        Bukkit.getLogger().warning("[StatisticsRewards] Entity " + textEntity.toUpperCase() + " in the reward: " + key + " is not valid!");
                        return;
                    }
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Entity is not defined in the reward: " + key + "!");
                    return;
                }
            }

            String textSound = configuration.getString(key + ".play-sound." + "sound");
            String textPitch = configuration.getString(key + ".play-sound." + "pitch");

            if (textSound != null && textPitch != null) {
                if (plugin.getCheckerUtil().isSound(textSound.toUpperCase())) {
                    reward.setSound(Sound.valueOf(textSound.toUpperCase()));
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Sound " + textSound.toUpperCase() + " in the reward: " + key + " is not valid!");
                    return;
                }
                if (plugin.getCheckerUtil().isInteger(textPitch)) {
                    reward.setPitch(Integer.valueOf(textPitch));
                } else {
                    Bukkit.getLogger().warning("[StatisticsRewards] Pitch " + textPitch + " in the reward: " + key + " is not valid!");
                    return;
                }
            }

            if (!configuration.getStringList(key + ".messages").isEmpty()) {
                reward.setMessages(configuration.getStringList(key + ".messages"));
            }

            if (configuration.contains(key + ".commands")) {
                if (!configuration.getStringList(key + ".commands." + "console").isEmpty()) {
                    for (String command : configuration.getStringList(key + ".commands." + "console")) {
                        reward.addCommand(new Command(command, CommandType.CONSOLE));
                    }
                }
                if (!configuration.getStringList(key + ".commands." + "player").isEmpty()) {
                    for (String command : configuration.getStringList(key + ".commands." + "player")) {
                        reward.addCommand(new Command(command, CommandType.PLAYER));
                    }
                }
            }
            rewards.add(reward);
        });

        if (rewards.isEmpty()) return;
        rewards = sort();
        Long end = System.currentTimeMillis();
        if (rewards.size() > 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + rewards.size() + " rewards successfully in " + (end-start) + "ms!");
        } else if (rewards.size() == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + rewards.size() + " reward successfully in " + (end-start) + "ms!");
        }
    }

    public void reload() {
        rewards.clear();
        load();
    }

    private Set<Reward> sort() {
        Comparator<Reward> rewardComparator = Comparator.comparingInt(Reward::getAmount);
        return rewards.stream().sorted(rewardComparator).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Reward getReward(String name) {
        return rewards.stream().filter(reward -> reward.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Set<Reward> getRewards() {
        return rewards;
    }
}
