package de.TebosBrime.translate;

import de.TebosBrime.translate.api.DefaultTranslatorAPI;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class TranslatorAddon extends LabyModAddon {

    //TODO Known Bugs
    /*
        Leere Zeile beim autotranslate
        Probleme mit anderen Addons
    */

    private static TranslatorAddon translatorAddon;
    private static Settings settings;
    private static DefaultTranslatorAPI translator;
    private static Listener listener;

    @Override
    public void onEnable() {
        System.out.println("============================================");
        System.out.println(" Activate Chat Translator Addon for LabyMod ");
        System.out.println("============================================");

        translatorAddon = this;
        listener = new Listener();

        System.out.println("============================================");
        System.out.println(" Enabled Chat Translator Addon for LabyMod  ");
        System.out.println("============================================");
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        settings = new Settings(list);
        resetTranslator();
    }

    public void resetTranslator(){
        translator = TranslatorManager.getTrangslator(getSettings().getTranslatorAsInt());
        translator.setAPIKey(settings.getApiKey());
        translator.setOutputLanguage(settings.getLanguageAsInt());
    }

    public static TranslatorAddon getInstance(){
        return translatorAddon;
    }

    public static Settings getSettings(){
        return settings;
    }

    public static DefaultTranslatorAPI getTranslator(){
        return translator;
    }

    public static Listener getListener() {
        return listener;
    }
}
