package me.nickax.statisticsrewards.data.storage;

import me.nickax.statisticsrewards.StatisticsRewards;
import me.nickax.statisticsrewards.config.enums.ConfigOption;
import me.nickax.statisticsrewards.data.object.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class MysqlStorageManager {

    private final StatisticsRewards plugin;

    private Connection connection;

    public MysqlStorageManager(StatisticsRewards plugin) {
        this.plugin = plugin;
    }

    public void inicialize() {
        String host = plugin.getConfigManager().getString(ConfigOption.MYSQL_HOST);
        String port = String.valueOf(plugin.getConfigManager().getInteger(ConfigOption.MYSQL_PORT));
        String username = plugin.getConfigManager().getString(ConfigOption.MYSQL_USERNAME);
        String password = plugin.getConfigManager().getString(ConfigOption.MYSQL_PASSWORD);
        String database = plugin.getConfigManager().getString(ConfigOption.MYSQL_DATABASE);
        boolean ssl = plugin.getConfigManager().getBoolean(ConfigOption.MYSQL_SSL);
        try {
            Long start = System.currentTimeMillis();
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Bukkit.getLogger().info("[StatisticsRewards] Trying to connect to the database...");
            connection = DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database + "?useSSL=" + ssl + "&autoReconnect=true", username, password);
            createTables();
            migrate();
            plugin.setMysqlEnabled(true);
            Long end = System.currentTimeMillis();
            Bukkit.getLogger().info("[StatisticsRewards] Connected to the database successfully in " + (end-start) + "ms!");
        } catch (SQLException e) {
            Bukkit.getLogger().warning("[StatisticsRewards] An error as occurred connecting to the database:");
            e.printStackTrace();
        }
    }

    public void save(Player player) {
        if (connection == null) return;

        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        try {
            if (!exist(player)) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playerdata(Name, UUID, Language, ReceivedRewards) VALUES(?, ?, ?, ?)");
                preparedStatement.setString(1, player.getName());
                preparedStatement.setString(2, player.getUniqueId().toString());
                if (playerData.getLanguage() != null) {
                    preparedStatement.setString(3, playerData.getLanguage());
                } else {
                    preparedStatement.setString(3, "");
                }
                if (!playerData.getReceivedRewards().isEmpty()) {
                    StringBuilder receivedRewards = new StringBuilder();
                    String comma = "";
                    for (String reward : playerData.getReceivedRewards()) {
                        receivedRewards.append(comma).append(reward);
                        comma = ", ";
                    }
                    preparedStatement.setString(4, receivedRewards.toString());
                } else {
                    preparedStatement.setString(4, "");
                }
                preparedStatement.executeUpdate();
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE playerdata SET Language=?, ReceivedRewards=? WHERE UUID=?");
                preparedStatement.setString(3, player.getUniqueId().toString());
                if (playerData.getLanguage() != null) {
                    preparedStatement.setString(1, playerData.getLanguage());
                } else {
                    preparedStatement.setString(1, "");
                }
                if (!playerData.getReceivedRewards().isEmpty()) {
                    StringBuilder receivedRewards = new StringBuilder();
                    String comma = "";
                    for (String reward : playerData.getReceivedRewards()) {
                        receivedRewards.append(comma).append(reward);
                        comma = ", ";
                    }
                    preparedStatement.setString(2, receivedRewards.toString());
                } else {
                    preparedStatement.setString(2, "");
                }
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("[StatisticsRewards] An error as occurred saving the " + player.getName() + " data!");
            e.printStackTrace();
        }
    }

    public void restore(Player player) {
        if (connection == null) return;

        PlayerData playerData = plugin.getDataManager().getPlayerData(player);
        if (playerData == null) return;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
            preparedStatement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String receivedRewards = resultSet.getString("ReceivedRewards");
                if (receivedRewards != null) {
                    String[] split = receivedRewards.split(", ");
                    for (String reward : split) {
                        playerData.addReceivedReward(reward);
                    }
                }

                String language = resultSet.getString("language");
                if (language != null) {
                    playerData.setLanguage(language);
                }
            }
        } catch (SQLException e) {
            Bukkit.getLogger().warning("[StatisticsRewards] An error as occurred restoring the " + player.getName() + " data!");
        }
    }

    private void migrate() {
        File folder = new File(plugin.getDataFolder() + "/playerdata");

        if (!folder.exists()) return;
        if (folder.listFiles() == null) return;

        for (File file : Objects.requireNonNull(folder.listFiles())) {

            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String name = configuration.getString("name");
            String uuid = configuration.getString("uuid");

            String language = null;
            if (configuration.contains("language")) {
                language = configuration.getString("language");
            }

            StringBuilder receivedRewards = null;
            if (!configuration.getStringList("received-rewards").isEmpty()) {
                receivedRewards = new StringBuilder();
                String comma = "";
                for (String reward : configuration.getStringList("received-rewards")) {
                    receivedRewards.append(comma).append(reward);
                    comma = ", ";
                }
            }

            try {
                if (file.delete()) {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playerdata(Name, UUID, Language, ReceivedRewards) VALUES(?, ?, ?, ?)");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, uuid);
                    if (language != null) {
                        preparedStatement.setString(3, language);
                    } else {
                        preparedStatement.setString(3, "");
                    }
                    if (receivedRewards != null) {
                        preparedStatement.setString(4, receivedRewards.toString());
                    } else {
                        preparedStatement.setString(4, "");
                    }
                    preparedStatement.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean exist(Player player) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
        preparedStatement.setString(1, player.getUniqueId().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private void createTables() throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS playerdata" +
                "(Name varchar(20), UUID varchar(36), Language varchar(2), ReceivedRewards varchar(10000), PRIMARY KEY(UUID))");
        preparedStatement.executeUpdate();
    }
}
