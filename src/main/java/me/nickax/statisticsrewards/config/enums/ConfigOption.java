package me.nickax.statisticsrewards.config.enums;

public enum ConfigOption {

    // General:
    CHECK_FOR_UPDATES("general.check-for-updates"),

    // Language:
    DETECT_CLIENT_LANGUAGE_ENABLED("language.detect-client-language"),
    DEFAULT_LANGUAGE("language.default-language"),

    // Data:
    DATA_AUTO_SAVE_ENABLED("data.auto-save"),
    DATA_AUTO_SAVE_DELAY("data.save-delay"),

    // Mysql:
    MYSQL_ENABLED("mysql.enabled"),
    MYSQL_HOST("mysql.host"),
    MYSQL_PORT("mysql.port"),
    MYSQL_USERNAME("mysql.username"),
    MYSQL_PASSWORD("mysql.password"),
    MYSQL_DATABASE("mysql.database"),
    MYSQL_SSL("mysql.ssl");

    private final String string;

    ConfigOption(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
