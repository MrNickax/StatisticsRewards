package me.nickax.statisticsrewards.statistic;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomStatisticIncrementEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean canceled;

    private final Player player;
    private final Statistic statistic;
    private final Integer newValue;
    private final Integer oldValue;

    public CustomStatisticIncrementEvent(Player player, Statistic statistic, Integer oldValue, Integer newValue) {
        this.canceled = false;
        this.player = player;
        this.statistic = statistic;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        canceled = cancel;
    }

    public Player getPlayer() {
        return player;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Integer getNewValue() {
        return newValue;
    }

    public Integer getOldValue() {
        return oldValue;
    }
}
