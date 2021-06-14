package me.nickax.statisticsrewards.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.data.DataManager;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.Map;

public class Placeholders extends PlaceholderExpansion {


    private final StatisticsRewards plugin;

    public Placeholders(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "statisticsrewards";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }


    @Override
    public String onPlaceholderRequest(Player player, String parameters){

        if (player == null) {
            return "Player not found!";
        }

        if (parameters.startsWith("is-collected_")) {
            String placeholder = parameters.replace("is-collected_","");
            DataManager dataManager = plugin.getPlayerData().getPlayer(player);
            if (dataManager.getCollectedRewards().containsKey(placeholder)) {
                if (dataManager.isClaimed(placeholder)) {
                    return "true";
                } else {
                    return "false";
                }
            } else {
                return "Reward not found!";
            }
        }

        return null;
    }
}