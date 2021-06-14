package me.nickax.statisticsrewards.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData {

    private final Map<UUID, DataManager> playerdata;

    public PlayerData() {
        this.playerdata = new HashMap<>();
    }

    public DataManager getPlayer(Player player) {
        return playerdata.get(player.getUniqueId());
    }

    public void addPlayerData(Player player, DataManager dataManager) {
        this.playerdata.put(player.getUniqueId(), dataManager);
    }

    public Map<UUID, DataManager> getPlayerDataMap() {
        return playerdata;
    }

}
