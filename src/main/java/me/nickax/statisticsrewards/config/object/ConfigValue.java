package me.nickax.statisticsrewards.config.object;

public class ConfigValue {

    private final Object value;

    public ConfigValue(Object value) {
        this.value = value;
    }

    public String getString() {
        return (String) value;
    }

    public Boolean getBoolean() {
        return (Boolean) value;
    }

    public Integer getInteger() {
        return (Integer) value;
    }

    public Double getDouble() {
        return (Double) value;
    }
}
