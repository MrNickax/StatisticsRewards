package me.nickax.statisticsrewards.support;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.object.PlayerData;
import me.nickax.statisticsrewards.rewards.object.Reward;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    private final StatisticsRewards plugin;

    public Placeholders(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull
    String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull
    String getIdentifier() {
        return "statisticsrewards";
    }

    @Override
    public @NotNull
    String getVersion() {
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String parameters) {

        if (player == null) {
            return "null";
        }

        if (parameters.startsWith("is-collected_")) {
            PlayerData playerData = plugin.getDataManager().getPlayerData(player);
            if (playerData == null) return "null";

            String placeholder = parameters.replace("is-collected_", "");

            if (playerData.getReceivedRewards().contains(placeholder)) {
                return "true";
            } else {
                return "false";
            }
        }

        if (parameters.startsWith("progress-bar_")) {
            PlayerData playerData = plugin.getDataManager().getPlayerData(player);
            if (playerData == null) return "null";

            String placeholder = parameters.replace("progress-bar_", "");
            String symbol = "|";
            int size = 20;

            String[] split = placeholder.split(":");

            Reward reward = plugin.getRewardsManager().getReward(split[0]);
            if (reward == null) return "null";

            if (split.length == 2) {
                symbol = split[1];
            } else if (split.length == 3) {
                symbol = split[1];
                if (plugin.getCheckerUtil().isInteger(split[2])) {
                    size = Integer.parseInt(split[2]);
                }
            }

            int progress;
            int goal = reward.getAmount();

            if (reward.getMaterial() != null) {
                progress = player.getStatistic(reward.getStatistic(), reward.getMaterial());
            } else if (reward.getEntityType() != null) {
                progress = player.getStatistic(reward.getStatistic(), reward.getEntityType());
            } else {
                progress = player.getStatistic(reward.getStatistic());
            }

            if (progress < 0) return "null";

            return plugin.getTextUtil().generateBar(progress, goal, size, symbol);
        }

        if (parameters.startsWith("percent_")) {
            PlayerData playerData = plugin.getDataManager().getPlayerData(player);
            if (playerData == null) return "null";

            String placeholder = parameters.replace("percent_", "");

            Reward reward = plugin.getRewardsManager().getReward(placeholder);
            if (reward == null) return "null";

            int progress;
            int goal = reward.getAmount();

            if (reward.getMaterial() != null) {
                progress = player.getStatistic(reward.getStatistic(), reward.getMaterial());
            } else if (reward.getEntityType() != null) {
                progress = player.getStatistic(reward.getStatistic(), reward.getEntityType());
            } else {
                progress = player.getStatistic(reward.getStatistic());
            }

            if (progress < 0) return "null";
            int percent = (progress*100)/goal;

            return percent + "%";
        }

        if (parameters.startsWith("goal_")) {
            PlayerData playerData = plugin.getDataManager().getPlayerData(player);
            if (playerData == null) return "null";

            String placeholder = parameters.replace("goal_", "");
            Reward reward = plugin.getRewardsManager().getReward(placeholder);

            if (reward == null) return "null";
            return reward.getAmount().toString();
        }

        if (parameters.startsWith("get_")) {
            String placeholder = parameters.replace("get_", "");
            String[] split = placeholder.split(":");

            if (split.length == 1) {
                if (!plugin.getCheckerUtil().isStatistic(split[0].toUpperCase())) return "null";

                Statistic statistic = Statistic.valueOf(split[0].toUpperCase());
                if (statistic.getType().equals(Statistic.Type.UNTYPED)) {
                    return String.valueOf(player.getStatistic(statistic));
                } else {
                    return "null";
                }
            } else if (split.length == 2) {
                if (!plugin.getCheckerUtil().isStatistic(split[0].toUpperCase())) return "null";

                Statistic statistic = Statistic.valueOf(split[0].toUpperCase());
                if (statistic.getType().equals(Statistic.Type.ITEM) || statistic.getType().equals(Statistic.Type.BLOCK)) {
                    if (!plugin.getCheckerUtil().isMaterial(split[1].toUpperCase())) return "null";

                    Material material = Material.valueOf(split[1].toUpperCase());
                    return String.valueOf(player.getStatistic(statistic, material));
                } else if (statistic.getType().equals(Statistic.Type.ENTITY)) {
                    if (!plugin.getCheckerUtil().isEntity(split[1].toUpperCase())) return "null";

                    EntityType entityType = EntityType.valueOf(split[1].toUpperCase());
                    return String.valueOf(player.getStatistic(statistic, entityType));
                } else {
                    return "null";
                }
            }
        }
        return "null";
    }
}