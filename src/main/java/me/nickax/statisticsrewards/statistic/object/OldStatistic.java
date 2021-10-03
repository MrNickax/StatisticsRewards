package me.nickax.statisticsrewards.statistic.object;

import org.bukkit.Statistic;

public class OldStatistic {

    private final Statistic statistic;
    private Integer amount;

    public OldStatistic(Statistic statistic, Integer amount) {
        this.statistic = statistic;
        this.amount = amount;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
