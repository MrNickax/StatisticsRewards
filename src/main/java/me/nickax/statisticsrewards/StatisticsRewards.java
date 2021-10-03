package me.nickax.statisticsrewards;

import me.nickax.statisticsrewards.command.CommandManager;
import me.nickax.statisticsrewards.config.ConfigFile;
import me.nickax.statisticsrewards.config.ConfigManager;
import me.nickax.statisticsrewards.config.enums.ConfigOption;
import me.nickax.statisticsrewards.data.DataManager;
import me.nickax.statisticsrewards.data.converter.OldYamlDataConverter;
import me.nickax.statisticsrewards.data.listener.DataCreationListeners;
import me.nickax.statisticsrewards.data.object.PlayerData;
import me.nickax.statisticsrewards.data.storage.MysqlStorageManager;
import me.nickax.statisticsrewards.data.storage.StorageManager;
import me.nickax.statisticsrewards.data.storage.YamlStorageManager;
import me.nickax.statisticsrewards.lang.LangFile;
import me.nickax.statisticsrewards.lang.LangManager;
import me.nickax.statisticsrewards.lang.listeners.LangJoinListeners;
import me.nickax.statisticsrewards.rewards.RewardsFile;
import me.nickax.statisticsrewards.rewards.RewardsManager;
import me.nickax.statisticsrewards.rewards.listeners.RewardsListeners;
import me.nickax.statisticsrewards.statistic.CustomOldStatisticManager;
import me.nickax.statisticsrewards.statistic.CustomStatisticIncrementTask;
import me.nickax.statisticsrewards.statistic.listener.CustomStatisticIncrementInitialization;
import me.nickax.statisticsrewards.statistic.listener.CustomStatisticIncrementListener;
import me.nickax.statisticsrewards.support.Placeholders;
import me.nickax.statisticsrewards.util.CheckerUtil;
import me.nickax.statisticsrewards.util.FileUtil;
import me.nickax.statisticsrewards.util.PlayerUtil;
import me.nickax.statisticsrewards.util.TextUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class StatisticsRewards extends JavaPlugin {

    // Files:
    private final ConfigFile configFile;
    private final LangFile langFile;
    private final RewardsFile rewardsFile;

    // Managers:
    private final ConfigManager configManager;
    private final LangManager langManager;
    private final CustomOldStatisticManager customOldStatisticManager;
    private final DataManager dataManager;
    private final StorageManager storageManager;
    private final YamlStorageManager yamlStorageManager;
    private final MysqlStorageManager mysqlStorageManager;
    private final RewardsManager rewardsManager;

    // Util:
    private final FileUtil fileUtil;
    private final TextUtil textUtil;
    private final PlayerUtil playerUtil;
    private final CheckerUtil checkerUtil;

    // Task:
    private final CustomStatisticIncrementTask customStatisticIncrementTask;

    // Converters:
    private final OldYamlDataConverter oldYamlDataConverter;

    // General:
    private boolean mysqlEnabled = false;

    public StatisticsRewards() {
        configFile = new ConfigFile(this);
        langFile = new LangFile(this);
        rewardsFile = new RewardsFile(this);
        configManager = new ConfigManager(this);
        langManager = new LangManager(this);
        customOldStatisticManager = new CustomOldStatisticManager();
        dataManager = new DataManager();
        storageManager = new StorageManager(this);
        yamlStorageManager = new YamlStorageManager(this);
        mysqlStorageManager = new MysqlStorageManager(this);
        rewardsManager = new RewardsManager(this);
        fileUtil = new FileUtil();
        textUtil = new TextUtil();
        playerUtil = new PlayerUtil();
        checkerUtil = new CheckerUtil(this);
        customStatisticIncrementTask = new CustomStatisticIncrementTask(this);
        oldYamlDataConverter = new OldYamlDataConverter(this);
    }


    @Override
    public void onEnable() {
        // Check for dependencies:
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).register();
            Bukkit.getLogger().info("[StatisticsRewards] PlaceholderAPI support enabled!");
        }

        // Load the configuration file:
        configFile.load();

        // Load the configuration values:
        configManager.load();

        // Load the language files:
        langFile.load();

        // Load the language values:
        langManager.load();

        // Load the rewards file:
        rewardsFile.load();

        // Load the rewards values:
        rewardsManager.load();

        // Convert the old data:
        oldYamlDataConverter.convert();

        // Try to load mysql if is enabled:
        if (configManager.getBoolean(ConfigOption.MYSQL_ENABLED)) {
            mysqlStorageManager.inicialize();
        }

        // Restore the data:
        for (Player player : Bukkit.getOnlinePlayers()) {
            dataManager.addPlayerData(player);
            storageManager.restore(player);
        }

        // Auto save the data:
        if (configManager.getBoolean(ConfigOption.DATA_AUTO_SAVE_ENABLED)) {
            int delay = configManager.getInteger(ConfigOption.DATA_AUTO_SAVE_DELAY)*1200;
            new BukkitRunnable() {
                public void run() {
                    for (PlayerData playerData : dataManager.getPlayerDataMap().values()) {
                        storageManager.save(playerData.getPlayer());
                    }
                }
            }.runTaskTimerAsynchronously(this, delay, delay);
        }

        // Load the players old statistics values:
        for (Player player : Bukkit.getOnlinePlayers()) {
            customOldStatisticManager.load(player);
        }

        // Start the play_one_minute statistic task:
        for (Player player : Bukkit.getOnlinePlayers()) {
            customStatisticIncrementTask.start(player);
        }

        // Register the Listeners:
        registerListeners();

        // Register the Commands:
        registerCommands();

        // BStats:
        new Metrics(this, 11650);

        // Check for updates:
        if (configManager.getBoolean(ConfigOption.CHECK_FOR_UPDATES)) {
            checkForUpdates();
        }
    }

    @Override
    public void onDisable() {
        // Save the data:
        for (PlayerData playerData : dataManager.getPlayerDataMap().values()) {
            storageManager.save(playerData.getPlayer());
        }
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();

        // Data Listeners:
        pm.registerEvents(new DataCreationListeners(this), this);

        // Lang Listeners:
        pm.registerEvents(new LangJoinListeners(this), this);

        // Custom Statistic Increment Listeners:
        pm.registerEvents(new CustomStatisticIncrementListener(this), this);
        pm.registerEvents(new CustomStatisticIncrementInitialization(this), this);

        // Rewards Listeners:
        pm.registerEvents(new RewardsListeners(this), this);

    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("statisticsrewards")).setExecutor(new CommandManager(this));
        Objects.requireNonNull(getCommand("statisticsrewards")).setTabCompleter(new CommandManager(this));
    }

    private void checkForUpdates() {
        checkerUtil.getVersion(93283, version -> {
            int spigot = Integer.parseInt(version.replace(".", ""));
            int plugin = Integer.parseInt(getDescription().getVersion().replace(".", ""));
            if (spigot-plugin > 0) {
                Bukkit.getLogger().info("[StatisticsRewards] There is a new update available! Current version: " + getDescription().getVersion() + " Last Version: " + version);
                Bukkit.getLogger().info("[StatisticsRewards] Download it at spigot: https://spigotmc.org/resources/93283");
            }
        });
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public LangFile getLangFile() {
        return langFile;
    }

    public RewardsFile getRewardsFile() {
        return rewardsFile;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public FileUtil getFileUtil() {
        return fileUtil;
    }

    public TextUtil getTextUtil() {
        return textUtil;
    }

    public PlayerUtil getPlayerUtil() {
        return playerUtil;
    }

    public CheckerUtil getCheckerUtil() {
        return checkerUtil;
    }

    public CustomOldStatisticManager getCustomOldStatisticManager() {
        return customOldStatisticManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public YamlStorageManager getYamlStorageManager() {
        return yamlStorageManager;
    }

    public MysqlStorageManager getMysqlStorageManager() {
        return mysqlStorageManager;
    }

    public RewardsManager getRewardsManager() {
        return rewardsManager;
    }

    public CustomStatisticIncrementTask getCustomStatisticIncrementTask() {
        return customStatisticIncrementTask;
    }

    public boolean isMysqlEnabled() {
        return mysqlEnabled;
    }

    public void setMysqlEnabled(boolean mysqlEnabled) {
        this.mysqlEnabled = mysqlEnabled;
    }
}
