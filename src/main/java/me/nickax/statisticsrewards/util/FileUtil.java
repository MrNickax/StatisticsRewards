package me.nickax.statisticsrewards.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class FileUtil {

    private String invalidReason = "Unknown";

    public boolean isValid(File file, FileConfiguration currentConfiguration, FileConfiguration defaultConfiguration) {

        AtomicBoolean valid = new AtomicBoolean(true);

        try {
            currentConfiguration.load(file);
            defaultConfiguration.getKeys(true).forEach(key -> {
                if (!currentConfiguration.contains(key)) {
                    setInvalidReason("Old Configuration File");
                    valid.set(false);
                }
            });
        } catch (InvalidConfigurationException e) {
            setInvalidReason("Invalid Configuration File");
            valid.set(false);
        } catch (Exception e) {
            valid.set(false);
        }

        return valid.get();
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    private void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }
}
