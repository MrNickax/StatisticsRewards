package me.nickax.statisticsrewards.lang;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.config.enums.ConfigOption;
import me.nickax.statisticsrewards.data.object.PlayerData;
import me.nickax.statisticsrewards.lang.enums.LangOption;
import me.nickax.statisticsrewards.lang.object.LangOptionL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LangManager {

    private final StatisticsRewards plugin;

    private final Map<LangOptionL, String> langValues;

    public LangManager(StatisticsRewards plugin) {
        this.plugin = plugin;
        this.langValues = new HashMap<>();
    }

    public void load() {
        Long start = System.currentTimeMillis();
        for (String language : plugin.getLangFile().getLanguages()) {
            File file = new File(plugin.getDataFolder() + "/lang/lang_" + language + ".yml");
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (LangOption langOption : LangOption.values()) {
                langValues.put(new LangOptionL(language, langOption), configuration.getString(langOption.getMessageType().toString().toLowerCase() + "." + langOption.toString().toLowerCase().replace("_", "-")));
            }
        }
        Long end = System.currentTimeMillis();
        if (langValues.size() > 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + langValues.size() + " language values successfully in " + (end-start) + "ms!");
        } else if (langValues.size() == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + langValues.size() + " language value successfully in " + (end-start) + "ms!");
        }
    }

    public void reload() {
        langValues.clear();
        load();
    }

    public String getLanguage(Player player) {
        String language;
        if (player != null && plugin.getDataManager().getPlayerData(player) != null && plugin.getDataManager().getPlayerData(player).getLanguage() != null) {
            language = plugin.getDataManager().getPlayerData(player).getLanguage();
        } else {
            String defaultLanguage = plugin.getConfigManager().getString(ConfigOption.DEFAULT_LANGUAGE);
            if (plugin.getLangFile().contains(defaultLanguage)) {
                language = defaultLanguage;
            } else {
                language = "en";
            }
        }
        return language;
    }

    public void setLanguage(Player player, String language) {
        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;
        playerData.setLanguage(language);
    }

    public String getLangValue(LangOption langOption, Player player) {
        return plugin.getTextUtil().color(langValues.get(getLangOptionL(langOption, getLanguage(player))));
    }

    private LangOptionL getLangOptionL(LangOption langOption, String language) {
        return langValues.keySet().stream().filter(langOptionL -> langOptionL.getLangOption().equals(langOption) && langOptionL.getLanguage().equalsIgnoreCase(language)).findFirst().orElse(null);
    }
}
