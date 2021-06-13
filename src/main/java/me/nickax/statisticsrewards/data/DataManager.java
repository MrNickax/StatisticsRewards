package me.nickax.statisticsrewards.data;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DataManager {


    private final StatisticsRewards plugin;
    private final Player player;
    private final Map<String, Boolean> collectedRewards;

    public DataManager(Player player, StatisticsRewards plugin) {
        this.plugin = plugin;
        this.player = player;
        this.collectedRewards = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setCollectedRewards(String reward, Boolean collected) {
        collectedRewards.put(reward, collected);
    }

    public boolean isClaimed(String reward) {
        return collectedRewards.get(reward);
    }

    public Map<String, Boolean> getCollectedRewards() {
        return collectedRewards;
    }
}
