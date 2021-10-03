package me.nickax.statisticsrewards.lang;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.UUID;

public class LangFile {

    private final StatisticsRewards plugin;

    private final String[] languages;

    public LangFile(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.languages = new String[] {"en", "es"};
    }

    public void load() {
        Long start = System.currentTimeMillis();
        int i = 0;
        for (String language : languages) {
            File file = new File(plugin.getDataFolder() + "/lang/lang_" + language + ".yml");
            if (!file.exists()) {
                plugin.saveResource("lang/lang_" + language + ".yml", false);
            } else {
                InputStream inputStream = plugin.getResource("lang/lang_" + language + ".yml");
                if (inputStream != null) {
                    FileConfiguration defaultConfiguration = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
                    FileConfiguration currentConfiguration = YamlConfiguration.loadConfiguration(file);
                    if (!plugin.getFileUtil().isValid(file, currentConfiguration, defaultConfiguration)) {
                        String ID = String.valueOf(UUID.randomUUID()).split("-")[0];
                        if (file.renameTo(new File(plugin.getDataFolder() + "/lang/invalid-lang_" + language + "-" + ID + ".yml"))) {
                            plugin.saveResource("lang/lang_" + language + ".yml", false);
                            Bukkit.getLogger().warning("[StatisticsRewards] The lang_" + language + " file was not valid, a new file has been generated!");
                            Bukkit.getLogger().warning("                    The old language file name is now: invalid-lang_" + language + "-" + ID);
                            Bukkit.getLogger().warning("                    Invalid reason: " + plugin.getFileUtil().getInvalidReason());
                        }
                    }
                }
            }
            i++;
        }
        Long end = System.currentTimeMillis();
        if (i > 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + i + " languages successfully in " + (end-start) + "ms!");
        } else if (i == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + i + " language successfully in " + (end-start) + "ms!");
        }
    }

    public boolean contains(String language) {
        return Arrays.stream(languages).filter(lang -> lang.equalsIgnoreCase(language)).findFirst().orElse(null) != null;
    }

    public String[] getLanguages() {
        return languages;
    }
}
