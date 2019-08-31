package de.TebosBrime.translate;

import de.TebosBrime.translate.enums.EnumModuleLanguages;
import de.TebosBrime.translate.enums.EnumModuleTranslatorSources;
import de.TebosBrime.translate.utils.TextElement;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    private boolean enabled;
    private int lang;
    private String translation_icon;
    private boolean translateAtHover;
    private int delaySeconds;
    private String apiKey;
    private int translator_source;

    private List<SettingsElement> subSettings;

    Settings(List<SettingsElement> subSettings) {
        this.subSettings = subSettings;

        loadConfig();
        init();
    }

    private final String ENABLED = "enabled";
    private final String SOURCE = "source";
    private final String LANG = "lang";
    private final String ICON = "icon";
    private final String HOVER = "hover";
    private final String DELAY = "delay";
    private final String APIKEY = "apiKey";

    private void loadConfig(){
        this.enabled = TranslatorAddon.getInstance().getConfig().has(ENABLED) ? TranslatorAddon.getInstance().getConfig().get(ENABLED).getAsBoolean() : true;
        this.translator_source = TranslatorAddon.getInstance().getConfig().has(SOURCE) ? TranslatorAddon.getInstance().getConfig().get(SOURCE).getAsInt() : 0;
        this.lang = TranslatorAddon.getInstance().getConfig().has(LANG) ? TranslatorAddon.getInstance().getConfig().get(LANG).getAsInt() : 0;
        this.translation_icon = TranslatorAddon.getInstance().getConfig().has(ICON) ? TranslatorAddon.getInstance().getConfig().get(ICON).getAsString() : " &7[&gT&7]";
        this.translateAtHover = TranslatorAddon.getInstance().getConfig().has(HOVER) ? TranslatorAddon.getInstance().getConfig().get(HOVER).getAsBoolean() : false;
        this.delaySeconds = TranslatorAddon.getInstance().getConfig().has(DELAY) ? TranslatorAddon.getInstance().getConfig().get(DELAY).getAsInt() : 2;
        this.apiKey = TranslatorAddon.getInstance().getConfig().has(APIKEY) ? TranslatorAddon.getInstance().getConfig().get(APIKEY).getAsString() : "CHANGE_ME";
    }

    public void init(){
        subSettings.clear();

        subSettings.add(new HeaderElement("General"));
        subSettings.add(new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            enabled = aBoolean;
            saveConfig();
        }, enabled));

        DropDownMenu<EnumModuleTranslatorSources> alignmentDropDownMenu = new DropDownMenu<EnumModuleTranslatorSources>("Translator", 0, 0, 0, 0 ).fill(EnumModuleTranslatorSources.values());
        DropDownElement<EnumModuleTranslatorSources> alignmentDropDown = new DropDownElement<>("Translator", alignmentDropDownMenu );
        alignmentDropDownMenu.setSelected(EnumModuleTranslatorSources.fromInt(translator_source));
        alignmentDropDown.setChangeListener(alignment -> {
            translator_source = EnumModuleTranslatorSources.fromEnum(alignment);
            saveConfig();
        });
        subSettings.add(alignmentDropDown);

        String[] help;

        //TODO: added config refresh
        if(EnumModuleTranslatorSources.needKey(translator_source)) {
            if(translator_source == 2){
                help = new String[]{
                        "Thesaurus API can only translate single",
                        "words. May use /+translate <your word>",
                        ""
                };
                for (String s : help)
                    subSettings.add(new TextElement(s, 5, 10));
            }

            help = new String[]{
                    "An API key is required for this translator.",
                    "You can set it here.",
                    ""
            };
            for (String s : help)
                subSettings.add(new TextElement(s, 5, 10));

            StringElement apiKeyElement = new StringElement("API key", new ControlElement.IconData(Material.BOOK_AND_QUILL), apiKey, s -> {
                apiKey = s;
                saveConfig();
            });
            subSettings.add(apiKeyElement);
        }

        DropDownMenu<EnumModuleLanguages> alignmentDropDownMenu2 = new DropDownMenu<EnumModuleLanguages>("Translate to", 0, 0, 0, 0).fill(EnumModuleLanguages.values());
        DropDownElement<EnumModuleLanguages> alignmentDropDown2 = new DropDownElement<>("Translate to", alignmentDropDownMenu2);
        alignmentDropDownMenu2.setSelected(EnumModuleLanguages.fromInt(lang));
        alignmentDropDown2.setChangeListener(alignment -> {
            lang = EnumModuleLanguages.fromEnum(alignment);
            saveConfig();
        });
        subSettings.add(alignmentDropDown2);

        subSettings.add(new HeaderElement("Messages"));
        help = new String[] {
                "You can also use only spaces.",
                "Nothing is displayed, but the message",
                "can still be translated by clicking",
                "on the end of the sentence.",
                ""
        };
        for(String s : help)
            subSettings.add(new TextElement(s,5, 10));
        StringElement icon = new StringElement("Clickable text", new ControlElement.IconData(Material.PAPER), this.getTranslation_icon(), s -> {
            translation_icon = s;
            saveConfig();
        });
        subSettings.add(icon);

        subSettings.add(new HeaderElement("AutoTranslation"));
        help = new String[]{
                "Auto translation is a test feature.",
                "Use only, if you know, what you are doing.",
                "The translation is displayed when you",
                "hover over the message with the mouse.",
                "Messages may be displayed later / in wrong",
                "sequence. And with an empty line above.",
                "Use only on good computers!",
                "(min. 3GB free RAM) Disable any addon",
                "which modify the chat",
                ""
        };
        for(String s : help)
            subSettings.add(new TextElement(s,5, 10));
        subSettings.add(new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            translateAtHover = aBoolean;
            saveConfig();
        }, translateAtHover));

        NumberElement numberElement = new NumberElement( "Maximum message delay (1 - 10 seconds)", new ControlElement.IconData( Material.WATCH ), delaySeconds);
        numberElement.setRange(1, 10);
        numberElement.addCallback(accepted -> {
            delaySeconds = accepted;
            saveConfig();
        });
        subSettings.add( numberElement );
    }

    private void saveConfig(){
        TranslatorAddon.getInstance().getConfig().addProperty(ENABLED, this.enabled);
        TranslatorAddon.getInstance().getConfig().addProperty(SOURCE, this.translator_source);
        TranslatorAddon.getInstance().getConfig().addProperty(LANG, this.lang);
        TranslatorAddon.getInstance().getConfig().addProperty(ICON, this.translation_icon);
        TranslatorAddon.getInstance().getConfig().addProperty(HOVER, this.translateAtHover);
        TranslatorAddon.getInstance().getConfig().addProperty(DELAY, this.delaySeconds);
        TranslatorAddon.getInstance().getConfig().addProperty(APIKEY, this.apiKey);

        TranslatorAddon.getInstance().resetTranslator();
        TranslatorAddon.getListener().translations.clear();

        init();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public EnumModuleTranslatorSources getTranslatorAsValue(){
        return EnumModuleTranslatorSources.fromInt(this.translator_source);
    }

    public int getTranslatorAsInt(){
        return translator_source;
    }

    public EnumModuleLanguages getLanguageAsValue(){
        return EnumModuleLanguages.fromInt(this.lang);
    }

    public int getLanguageAsInt(){
        return this.lang;
    }

    public String getTranslation_icon() {
        return translation_icon;
    }

    public boolean isTranslateAtHover() {
        return translateAtHover;
    }

    public int getDelaySeconds() {
        return delaySeconds;
    }

    public String getApiKey(){
        return apiKey;
    }
}
