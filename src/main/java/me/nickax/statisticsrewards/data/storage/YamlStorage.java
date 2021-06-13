package me.nickax.statisticsrewards.data.storage;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;

public class YamlStorage {

    private final StatisticsRewards plugin;
    private final File folder;

    public YamlStorage (StatisticsRewards plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getDataFolder() + "/playerdata");
    }

    public void save (Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        DataManager playerData = plugin.getPlayerData().getPlayer(player);
        if (playerData != null) {
            if (!playerData.getCollectedRewards().isEmpty()) {
                if (!folder.exists()) {
                    try {
                        folder.mkdir();
                    } catch (Exception e) {
                        Bukkit.getLogger().warning("[StatisticsRewards] An error has creating the data folder, if you see this error, report it to the developer or the data of your players could be in danger!");
                        return;
                    }
                }
                if (folder.exists()) {
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (Exception e) {
                            Bukkit.getLogger().warning("[StatisticsRewards] An error has occurred saving the " + player.getName() + " data, if you see this error, report it to the developer or the data of your players could be in danger!");
                            return;
                        }
                    }
                }
                FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
                try {
                    fileConfiguration.set("uuid", player.getUniqueId().toString());
                    fileConfiguration.set("name", player.getName());
                    for (Map.Entry<String, Boolean> collectedRewards : playerData.getCollectedRewards().entrySet()) {
                        if (fileConfiguration.contains("collected-rewards")) {
                            if (fileConfiguration.contains("collected-rewards." + collectedRewards.getKey())) {
                                if (fileConfiguration.getBoolean("collected-rewards." + collectedRewards.getKey()) != collectedRewards.getValue()) {
                                    fileConfiguration.set("collected-rewards." + collectedRewards.getKey(), collectedRewards.getValue());
                                    fileConfiguration.save(file);
                                }
                            } else {
                                fileConfiguration.set("collected-rewards." + collectedRewards.getKey(), collectedRewards.getValue());
                                fileConfiguration.save(file);
                            }
                        } else {
                            fileConfiguration.set("collected-rewards." + collectedRewards.getKey(), collectedRewards.getValue());
                            fileConfiguration.save(file);
                        }
                    }
                } catch (Exception e) {
                    Bukkit.getLogger().warning("[StatisticsRewards] An error has occurred saving the " + player.getName() + " data, if you see this error, report it to the developer or the data of your players could be in danger!");
                }
            }
        }
    }

    public void restore(Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        if (file.exists()) {
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
            DataManager playerdata = plugin.getPlayerData().getPlayer(player);
            try {
                fileConfiguration.getConfigurationSection("collected-rewards").getKeys(false).forEach(key -> playerdata.setCollectedRewards(key, fileConfiguration.getBoolean("collected-rewards." + key)));
            } catch (Exception e) {
                Bukkit.getLogger().warning("[StatisticsRewards] An error has occurred restoring the " + player.getName() + " data, if you see this error, report it to the developer or the data of your players could be in danger!");
            }
        }
    }

    public FileConfiguration config(Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }
}
