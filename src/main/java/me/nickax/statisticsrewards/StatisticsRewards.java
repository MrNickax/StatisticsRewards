package me.nickax.statisticsrewards;

import me.nickax.statisticsrewards.commands.CommandAutoComplete;
import me.nickax.statisticsrewards.commands.CommandManager;
import me.nickax.statisticsrewards.config.ConfigManager;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.data.DataUtils;
import me.nickax.statisticsrewards.data.PlayerManager;
import me.nickax.statisticsrewards.data.listeners.DataListeners;
import me.nickax.statisticsrewards.data.storage.YamlStorage;
import me.nickax.statisticsrewards.lang.LangManager;
import me.nickax.statisticsrewards.lang.MessageManager;
import me.nickax.statisticsrewards.placeholders.Placeholders;
import me.nickax.statisticsrewards.rewards.RewardManager;
import me.nickax.statisticsrewards.rewards.listeners.RewardsListeners;
import me.nickax.statisticsrewards.util.Checkers;
import me.nickax.statisticsrewards.util.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public final class StatisticsRewards extends JavaPlugin {

    private final RewardManager rewardManager = new RewardManager(this);
    private final PlayerManager playerManager = new PlayerManager();
    private final ConfigManager configManager = new ConfigManager(this);
    private final LangManager langManager = new LangManager(this);
    private final MessageManager messageManager = new MessageManager(this);
    private final YamlStorage yamlStorage = new YamlStorage(this);
    private final DataUtils dataUtils = new DataUtils(this);
    private final Checkers checkers = new Checkers(this);

    @Override
    public void onEnable() {
        // Check for dependencies:
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).register();
            Bukkit.getLogger().info("[StatisticsRewards] PlaceholderAPI support enabled!");
        }
        // Load Config:
        configManager.load();
        // Load Rewards:
        rewardManager.load();
        // Register Commands:
        Long s = System.currentTimeMillis();
        registerCommands();
        CommandManager commandManager = new CommandManager();
        Long e = System.currentTimeMillis();
        if (commandManager.getPlayerCommands().size() == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + commandManager.getPlayerCommands().size() + " command successfully in " + (e-s) + "ms!");
        } else if (commandManager.getPlayerCommands().size() > 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + commandManager.getPlayerCommands().size() + " commands successfully in " + (e-s) + "ms!");
        }
        // Data:
        // - Create Data;
        for (Player player : Bukkit.getOnlinePlayers()) {
            dataUtils.createData(player);
        }
        // - Restore Data:
        for (Player player : Bukkit.getOnlinePlayers()) {
            yamlStorage.restore(player);
        }
        // - Save Data:
        if (configManager.config().getBoolean("data-auto-save")) {
            int delay = configManager.config().getInt("data-save-delay");
            new BukkitRunnable() {
                public void run() {
                    for (DataManager dataManager : playerManager.getPlayerData().values()) {
                        yamlStorage.save(dataManager.getPlayer());
                    }
                }
            }.runTaskTimer(this, 0L, delay);
        }
        // Load Lang:
        langManager.load();
        // Register Events:
        registerEvents();
        // BStats:
        int pluginId = 11650;
        new Metrics(this, pluginId);
        // Check for updates:
        if (configManager.config().getBoolean("check-for-updates")) {
            checkForUpdates();
        }
    }

    @Override
    public void onDisable() {
        // Data:
        // - Save Data:
        for (DataManager dataManager : playerManager.getPlayerData().values()) {
            yamlStorage.save(dataManager.getPlayer());
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new DataListeners(this), this);
        pm.registerEvents(new RewardsListeners(this), this);
    }

    private void registerCommands() {
        getCommand("statisticsrewards").setExecutor(new CommandManager());
        getCommand("statisticsrewards").setTabCompleter(new CommandAutoComplete());
    }

    private void checkForUpdates() {
        Logger logger = this.getLogger();
        new UpdateChecker(this, 93283).getVersion(version -> {
            int spigot = Integer.parseInt(version.replace(".", ""));
            int plugin = Integer.parseInt(getDescription().getVersion().replace(".", ""));
            if (spigot-plugin > 0) {
                if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    logger.info("There is a new update available! Current version: " + getDescription().getVersion() + " Last Version: " + version);
                    logger.info("Download it at spigot: https://spigotmc.org/resources/93283");
                }
            }
        });
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public YamlStorage getYamlStorage() {
        return yamlStorage;
    }

    public DataUtils getDataUtils() {
        return dataUtils;
    }

    public Checkers getCheckers() {
        return checkers;
    }
}
