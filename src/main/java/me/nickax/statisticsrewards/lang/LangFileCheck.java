package me.nickax.statisticsrewards.lang;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;

public class LangFileCheck {

    private final StatisticsRewards plugin;
    private final File folder;

    public LangFileCheck(StatisticsRewards plugin) {
        this.plugin = plugin;
        folder = new File(plugin.getDataFolder() + "/lang");
    }

    public boolean checkValid(String lang) {
        File file = new File(plugin.getDataFolder() + "/lang/" + lang + ".yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        if (checkExist(lang)) {
            if (!fileConfiguration.contains("general-messages") || !fileConfiguration.contains("statistics-messages")) {
                return false;
            } else {
                return fileConfiguration.contains("general-messages." + ".reload-message") && fileConfiguration.contains("general-messages." + ".no-permission-message") && fileConfiguration.contains("general-messages." + ".unknown-command-message");
            }
        } else {
            return false;
        }
    }

    public boolean checkExist(String lang) {
        if (folder.exists()) {
            if (folder.listFiles() != null) {
                return Arrays.toString(folder.list()).contains(lang);
            }
        }
        return false;
    }
}
