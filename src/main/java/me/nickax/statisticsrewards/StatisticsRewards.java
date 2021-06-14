package me.nickax.statisticsrewards;

import me.nickax.statisticsrewards.commands.CommandAutoComplete;
import me.nickax.statisticsrewards.commands.CommandManager;
import me.nickax.statisticsrewards.config.ConfigManager;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.data.DataUtils;
import me.nickax.statisticsrewards.data.PlayerData;
import me.nickax.statisticsrewards.data.listeners.DataListeners;
import me.nickax.statisticsrewards.data.storage.YamlStorage;
import me.nickax.statisticsrewards.lang.LangFileCheck;
import me.nickax.statisticsrewards.lang.LangManager;
import me.nickax.statisticsrewards.lang.MessageManager;
import me.nickax.statisticsrewards.placeholderapi.Placeholders;
import me.nickax.statisticsrewards.statistics.RewardsListeners;
import me.nickax.statisticsrewards.statistics.RewardsManager;
import me.nickax.statisticsrewards.util.Checkers;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class StatisticsRewards extends JavaPlugin {

    private final PlayerData playerData = new PlayerData();
    private final YamlStorage yamlStorage = new YamlStorage(this);
    private final DataUtils dataUtils = new DataUtils(this);
    private final LangManager langManager = new LangManager(this);
    private final LangFileCheck langFileCheck = new LangFileCheck(this);
    private final ConfigManager configManager = new ConfigManager(this);
    private final RewardsManager rewardsManager = new RewardsManager(this);
    private final MessageManager messageManager = new MessageManager(this);
    private final Checkers checkers = new Checkers(this);

    @Override
    public void onEnable() {
        // Check for dependencies:
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).register();
            Bukkit.getLogger().info("[StatisticsRewards] PlaceholderAPI support enabled.");
        }
        // Load Config:
        configManager.load();
        // Load Statistics:
        rewardsManager.load();
        // Register Listeners:
        registerListeners();
        // Register Commands:
        Long s = System.currentTimeMillis();
        registerCommands();
        CommandManager commandManager = new CommandManager();
        Long e = System.currentTimeMillis();
        if (commandManager.getPlayerCommands().size() == 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + commandManager.getPlayerCommands().size() + " command successfully in " + (e-s) + "ms.");
        } else if (commandManager.getPlayerCommands().size() > 1) {
            Bukkit.getLogger().info("[StatisticsRewards] Loaded " + commandManager.getPlayerCommands().size() + " commands successfully in " + (e-s) + "ms.");
        }
        // Data:
        // Create Data:
        for (Player player : Bukkit.getOnlinePlayers()) {
            DataManager data = playerData.getPlayer(player);
            if (data == null) {
                dataUtils.createData(player);
            }
        }
        // Restore Data:
        for (Player player : Bukkit.getOnlinePlayers()) {
            DataManager data = playerData.getPlayer(player);
            if (data != null) {
                if (yamlStorage.config(player).contains("collected-rewards")) {
                    yamlStorage.restore(player);
                }
            }
        }
        // Save Data:
        if (getConfig().getBoolean("data-auto-save")) {
            int timer = getConfig().getInt("data-save-delay");
            new BukkitRunnable() {
                public void run() {
                    for (DataManager dataManager : playerData.getPlayerDataMap().values()) {
                        yamlStorage.save(dataManager.getPlayer());
                    }
                }
            }.runTaskTimer(this, 0, timer);
        }
        // Load Lang:
        langManager.load();
        // BStats:
        int pluginId = 11650;
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        // Data:
        // Save Data:
        for (DataManager dataManager : playerData.getPlayerDataMap().values()) {
            yamlStorage.save(dataManager.getPlayer());
        }
        configManager.reload();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new DataListeners(this), this);
        pluginManager.registerEvents(new RewardsListeners(this), this);
    }

    private void registerCommands() {
        getCommand("statisticsrewards").setExecutor(new CommandManager());
        getCommand("statisticsrewards").setTabCompleter(new CommandAutoComplete());
    }

    public Checkers getCheckers() {
        return checkers;
    }
    public RewardsManager getRewardsManager() {
        return rewardsManager;
    }

    public LangFileCheck getLangFileCheck() {
        return langFileCheck;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public DataUtils getDataUtils() {
        return dataUtils;
    }

    public YamlStorage getYamlStorage() {
        return yamlStorage;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
