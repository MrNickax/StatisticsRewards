package me.nickax.statisticsrewards.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DataManager {

    private final Player player;
    private final Map<String, Boolean> receivedRewards;

    public DataManager(Player player) {
        this.player = player;
        this.receivedRewards = new HashMap<>();
    }

    public boolean isReceived(String reward) {
        return receivedRewards.getOrDefault(reward, false);
    }

    public void setReceivedReward(String reward, Boolean received) {
        receivedRewards.put(reward, received);
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String, Boolean> getReceivedRewards() {
        return receivedRewards;
    }
}
