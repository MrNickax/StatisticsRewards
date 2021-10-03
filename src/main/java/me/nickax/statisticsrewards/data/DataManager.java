package me.nickax.statisticsrewards.data;

import me.nickax.statisticsrewards.data.object.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {

    private final Map<UUID, PlayerData> playerData;

    public DataManager() {
        this.playerData = new HashMap<>();
    }

    public PlayerData getPlayerData(Player player) {
        return playerData.get(player.getUniqueId());
    }

    public void addPlayerData(Player player) {
        playerData.put(player.getUniqueId(), new PlayerData(player));
    }

    public void removePlayerData(Player player) {
        playerData.remove(player.getUniqueId());
    }

    public Map<UUID, PlayerData> getPlayerDataMap() {
        return playerData;
    }
}
