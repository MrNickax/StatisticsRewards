package me.nickax.statisticsrewards.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.rewards.object.Reward;
import org.bukkit.ChatColor;
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
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier(){
        return "statisticsrewards";
    }

    @Override
    public @NotNull String getVersion(){
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String parameters){

        if (player == null) {
            return "null";
        }

        if (parameters.startsWith("is-collected_")) {
            String placeholder = parameters.replace("is-collected_","");
            DataManager dataManager = plugin.getPlayerManager().getPlayer(player);
            if (isReward(placeholder)) {
                if (dataManager.getReceivedRewards().containsKey(placeholder)) {
                    if (dataManager.isReceived(placeholder)) {
                        return "true";
                    } else {
                        return "false";
                    }
                } else {
                    return "false";
                }
            } else {
                return "null";
            }
        } else if (parameters.startsWith("progress-bar_")) {
            String placeholder = parameters.replace("progress-bar_","");
            String symbol = "|";
            int size = 20;
            if (placeholder.contains(":")) {
                try {
                    symbol = placeholder.split(":")[1];
                } catch (Exception e) {
                    // oof
                }
                try {
                    String number = placeholder.split(":")[2];
                    if (plugin.getCheckers().isInt(number)) {
                        size = Integer.parseInt(number);
                    }
                } catch (Exception e) {
                    // oof
                }
            }
            if (isReward(placeholder.split(":")[0])) {
                for (Reward reward : plugin.getRewardManager().getRewardList()) {
                    if (reward.getName().equalsIgnoreCase(placeholder.split(":")[0])) {
                        if (reward.getStatistic().getType().equals(Statistic.Type.BLOCK) || reward.getStatistic().getType().equals(Statistic.Type.ITEM)) {
                            int goal = reward.getAmount();
                            int progress = player.getStatistic(reward.getStatistic(), reward.getMaterial());
                            return ChatColor.translateAlternateColorCodes('&', plugin.getProgressBar().create(progress, goal, size, symbol));
                        } else if (reward.getStatistic().getType().equals(Statistic.Type.ENTITY)) {
                            int goal = reward.getAmount();
                            int progress = player.getStatistic(reward.getStatistic(), reward.getEntityType());
                            return ChatColor.translateAlternateColorCodes('&', plugin.getProgressBar().create(progress, goal, size, symbol));
                        } else if (reward.getStatistic().getType().equals(Statistic.Type.UNTYPED)) {
                            int goal = reward.getAmount();
                            int progress = player.getStatistic(reward.getStatistic());
                            return ChatColor.translateAlternateColorCodes('&', plugin.getProgressBar().create(progress, goal, size, symbol));
                        }
                    }
                }
            } else {
                return "null";
            }
        } else if (parameters.startsWith("percent_")) {
            String placeholder = parameters.replace("percent_","");
            if (isReward(placeholder)) {
                for (Reward reward : plugin.getRewardManager().getRewardList()) {
                    if (reward.getName().equalsIgnoreCase(placeholder)) {
                        if (reward.getStatistic().getType().equals(Statistic.Type.BLOCK) || reward.getStatistic().getType().equals(Statistic.Type.ITEM)) {
                            int goal = reward.getAmount();
                            int progress = player.getStatistic(reward.getStatistic(), reward.getMaterial());
                            int percent = progress * 100 / goal;
                            if (percent > 100) {
                                percent = 100;
                            }
                            return percent + "%";
                        } else if (reward.getStatistic().getType().equals(Statistic.Type.ENTITY)) {
                            int goal = reward.getAmount();
                            int progress = player.getStatistic(reward.getStatistic(), reward.getEntityType());
                            int percent = progress * 100 / goal;
                            if (percent > 100) {
                                percent = 100;
                            }
                            return percent + "%";
                        } else if (reward.getStatistic().getType().equals(Statistic.Type.UNTYPED)) {
                            int goal = reward.getAmount();
                            int progress = player.getStatistic(reward.getStatistic());
                            int percent = progress * 100 / goal;
                            if (percent > 100) {
                                percent = 100;
                            }
                            return percent + "%";
                        }
                    }
                }
            } else {
                return "null";
            }
        } else if (parameters.startsWith("goal_")) {
            String placeholder = parameters.replace("goal_","");
            if (isReward(placeholder)) {
                for (Reward reward : plugin.getRewardManager().getRewardList()) {
                    if (reward.getName().equalsIgnoreCase(placeholder)) {
                        int goal = reward.getAmount();
                        return "" + goal;
                    }
                }
            } else {
                return "null";
            }
        } else if (parameters.startsWith("get_")) {
            String placeholder = parameters.replace("get_","");
            if (plugin.getCheckers().isStatistic(placeholder.split(":")[0])) {
                Statistic statistic = Statistic.valueOf(placeholder.toUpperCase().split(":")[0]);
                if (statistic.getType().equals(Statistic.Type.BLOCK)) {
                    if (placeholder.contains(":")) {
                        if (plugin.getCheckers().isMaterial(placeholder.toUpperCase().split(":")[1])) {
                            Material material = Material.valueOf(placeholder.toUpperCase().split(":")[1]);
                            if (material.isBlock()) {
                                return "" + player.getStatistic(statistic, material);
                            } else {
                                return "null";
                            }
                        } else {
                            return "null";
                        }
                    } else {
                        return "null";
                    }
                } else if (statistic.getType().equals(Statistic.Type.ITEM)) {
                    if (placeholder.contains(":")) {
                        if (plugin.getCheckers().isMaterial(placeholder.toUpperCase().split(":")[1])) {
                            Material material = Material.valueOf(placeholder.toUpperCase().split(":")[1]);
                            if (material.isItem()) {
                                return "" + player.getStatistic(statistic, material);
                            } else {
                                return "null";
                            }
                        } else {
                            return "null";
                        }
                    } else {
                        return "null";
                    }
                } else if (statistic.getType().equals(Statistic.Type.ENTITY)) {
                    if (placeholder.contains(":")) {
                        if (plugin.getCheckers().isEntity(placeholder.toUpperCase().split(":")[1])) {
                            EntityType entityType = EntityType.valueOf(placeholder.toUpperCase().split(":")[1]);
                            if (entityType.isAlive()) {
                                return "" + player.getStatistic(statistic, entityType);
                            } else {
                                return "null";
                            }
                        } else {
                            return "null";
                        }
                    } else {
                        return "null";
                    }
                } else if (statistic.getType().equals(Statistic.Type.UNTYPED)) {
                    return "" + player.getStatistic(statistic);
                }
            } else {
                return "null";
            }
        }
        return null;
    }

    private boolean isReward(String name) {
        for (Reward reward : plugin.getRewardManager().getRewardList()) {
            if (reward.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}