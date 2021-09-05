package me.nickax.statisticsrewards.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final Map<UUID, DataManager> playerData;

    public PlayerManager() {
        this.playerData = new HashMap<>();
    }

    public void addData(Player player, DataManager dataManager) {
        this.playerData.put(player.getUniqueId(), dataManager);
    }

    public DataManager getPlayer(Player player) {
        return playerData.get(player.getUniqueId());
    }

    public Map<UUID, DataManager> getPlayerData() {
        return playerData;
    }
}
