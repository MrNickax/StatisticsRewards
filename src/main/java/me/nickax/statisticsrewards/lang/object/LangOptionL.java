package me.nickax.statisticsrewards.lang.object;

import me.nickax.statisticsrewards.lang.enums.LangOption;

public class LangOptionL {

    private final LangOption langOption;
    private final String language;

    public LangOptionL(String language, LangOption langOption) {
        this.langOption = langOption;
        this.language = language;
    }

    public LangOption getLangOption() {
        return langOption;
    }

    public String getLanguage() {
        return language;
    }
}
