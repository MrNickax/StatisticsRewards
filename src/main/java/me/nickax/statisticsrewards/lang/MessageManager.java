package me.nickax.statisticsrewards.lang;

import me.nickax.statisticsrewards.StatisticsRewards;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    private final StatisticsRewards plugin;

    public MessageManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void noPermission(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("general-messages." + ".no-permission-message")));
    }

    public void reload(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("general-messages." + ".reload-message")));
    }

    public void unknownCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("general-messages." + ".unknown-command-message")));
    }

    public void playerNotFound(CommandSender sender, String target) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("general-messages." + ".player-not-found-message")).replace("%player%", target));
    }

    public void notValidAmount(CommandSender sender, String amount) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("general-messages." + ".not-valid-amount-message")).replace("%amount%", amount));
    }

    public void statisticNotFound(CommandSender sender, String statistic) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".statistic-not-found-message")).replace("%statistic%", statistic));
    }

    public void blockNotFound(CommandSender sender, String block) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".block-not-found-message")).replace("%block%", block));
    }

    public void itemNotFound(CommandSender sender, String item) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".item-not-found-message")).replace("%item%", item));
    }

    public void entityNotFound(CommandSender sender, String entity) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".entity-not-found-message")).replace("%entity%", entity));
    }

    public void addStatistic(CommandSender sender, String target, int amount, String statistic, String entity_material) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".statistic-add-message")).replace("%player%", target).replace("%amount%", String.valueOf(amount)).replace("%statistic%", statistic).replace("%material%", entity_material));
    }

    public void setStatistic(CommandSender sender, String target, int amount, String statistic, String entity_material) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".statistic-set-message")).replace("%player%", target).replace("%amount%", String.valueOf(amount)).replace("%statistic%", statistic).replace("%material%", entity_material));
    }

    public void getStatistic(CommandSender sender, String target, int amount, String statistic, String entity_material) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".statistic-get-message")).replace("%player%", target).replace("%amount%", String.valueOf(amount)).replace("%statistic%", statistic).replace("%material%", entity_material));
    }

    public void addUntypedStatistic(CommandSender sender, String target, int amount, String statistic) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".statistic-add-untyped-message")).replace("%player%", target).replace("%amount%", String.valueOf(amount)).replace("%statistic%", statistic));
    }

    public void setUntypedStatistic(CommandSender sender, String target, int amount, String statistic) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".statistic-set-untyped-message")).replace("%player%", target).replace("%amount%", String.valueOf(amount)).replace("%statistic%", statistic));
    }

    public void getUntypedStatistic(CommandSender sender, String target, int amount, String statistic) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".statistic-get-untyped-message")).replace("%player%", target).replace("%amount%", String.valueOf(amount)).replace("%statistic%", statistic));
    }

    public void rewardNotFound(CommandSender sender, String reward) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".reward-not-found-message")).replace("%reward%", reward));
    }

    public void setCollected(CommandSender sender, String reward, Player player) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".reward-set-collected-message")).replace("%reward%", reward).replace("%player%", player.getName()));
    }

    public void setUncollected(CommandSender sender, String reward, Player player) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".reward-set-uncollected-message")).replace("%reward%", reward).replace("%player%", player.getName()));
    }

    public void setCollectedTarget(Player player, String reward) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".reward-set-collected-target-message")).replace("%reward%", reward));
    }

    public void setUncollectedTarget(Player player, String reward) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".reward-set-uncollected-target-message")).replace("%reward%", reward));
    }

    public void dataNotFound(CommandSender sender, Player player) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "" + plugin.getLangManager().getLang().getString("statistics-messages." + ".data-not-found-message")).replace("%player%", player.getName()));
    }
}
