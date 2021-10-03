package me.nickax.statisticsrewards.statistic;

import me.nickax.statisticsrewards.statistic.object.OldStatistic;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;

public class CustomOldStatisticManager {

    private final Map<Player, List<OldStatistic>> oldStatistics;

    public CustomOldStatisticManager() {
        this.oldStatistics = new HashMap<>();
    }

    public void load(Player player) {
        List<OldStatistic> oldStatistics = new ArrayList<>();
        for (Statistic statistic : Statistic.values()) {
            if (isUsableStatistic(statistic, false)) {
                oldStatistics.add(new OldStatistic(statistic, player.getStatistic(statistic)));
            }
        }
        this.oldStatistics.put(player, oldStatistics);
    }

    public void unload(Player player) {
        oldStatistics.remove(player);
    }

    public OldStatistic getOldStatistic(Player player, Statistic statistic) {
        return oldStatistics.get(player).stream().filter(oldStatistic -> oldStatistic.getStatistic().equals(statistic)).findFirst().orElse(null);
    }

    public boolean isUsableStatistic(Statistic statistic, boolean exclude) {
        if (exclude) {
            return statistic.equals(Statistic.WALK_ON_WATER_ONE_CM) || statistic.equals(Statistic.WALK_ONE_CM) ||
                    statistic.equals(Statistic.WALK_UNDER_WATER_ONE_CM) || statistic.equals(Statistic.CLIMB_ONE_CM) ||
                    statistic.equals(Statistic.CROUCH_ONE_CM) || statistic.equals(Statistic.SWIM_ONE_CM) ||
                    statistic.equals(Statistic.HORSE_ONE_CM) || statistic.equals(Statistic.AVIATE_ONE_CM) ||
                    statistic.equals(Statistic.BOAT_ONE_CM) || statistic.equals(Statistic.FALL_ONE_CM) ||
                    statistic.equals(Statistic.FLY_ONE_CM) || statistic.equals(Statistic.MINECART_ONE_CM) ||
                    statistic.equals(Statistic.SPRINT_ONE_CM) || statistic.equals(Statistic.STRIDER_ONE_CM) ||
                    statistic.equals(Statistic.PIG_ONE_CM);
        } else {
            return statistic.equals(Statistic.WALK_ON_WATER_ONE_CM) || statistic.equals(Statistic.WALK_ONE_CM) ||
                    statistic.equals(Statistic.WALK_UNDER_WATER_ONE_CM) || statistic.equals(Statistic.CLIMB_ONE_CM) ||
                    statistic.equals(Statistic.CROUCH_ONE_CM) || statistic.equals(Statistic.SWIM_ONE_CM) ||
                    statistic.equals(Statistic.HORSE_ONE_CM) || statistic.equals(Statistic.AVIATE_ONE_CM) ||
                    statistic.equals(Statistic.BOAT_ONE_CM) || statistic.equals(Statistic.FALL_ONE_CM) ||
                    statistic.equals(Statistic.FLY_ONE_CM) || statistic.equals(Statistic.MINECART_ONE_CM) ||
                    statistic.equals(Statistic.SPRINT_ONE_CM) || statistic.equals(Statistic.PLAY_ONE_MINUTE) ||
                    statistic.equals(Statistic.STRIDER_ONE_CM) || statistic.equals(Statistic.PIG_ONE_CM);
        }
    }
}
