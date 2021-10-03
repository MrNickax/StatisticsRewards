package me.nickax.statisticsrewards.data.object;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PlayerData {

    private final Player player;
    private String language;
    private final Set<String> receivedRewards;

    public PlayerData(Player player) {
        this.player = player;
        this.receivedRewards = new HashSet<>();
    }

    public Player getPlayer() {
        return player;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<String> getReceivedRewards() {
        return receivedRewards;
    }

    public void addReceivedReward(String reward) {
        receivedRewards.add(reward);
    }

    public void removeReceivedReward(String reward) {
        receivedRewards.remove(reward);
    }
}
