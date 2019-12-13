package de.TebosBrime.antirage;

import net.labymod.settings.elements.*;
import net.labymod.utils.Material;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Settings {

    private boolean enabled;
    private boolean exact;
    private int toggleKey;
    //private int menuKey;

    private List<SettingsElement> subSettings;

    Settings (List<SettingsElement> subSettings){
        this.subSettings = subSettings;

        loadConfig();
        createSettings();
    }

    private final String ENABLED = "enabled";
    private final String EXACT_CHECK = "exact_match";
    private final String TOGGLE_KEY = "toggle_key";
    //private final String MENU_KEY = "menu_key";

    private void loadConfig(){
        this.enabled = AntiRageAddon.getInstance().getConfig().has(ENABLED) ? AntiRageAddon.getInstance().getConfig().get(ENABLED).getAsBoolean() : true;
        this.exact = AntiRageAddon.getInstance().getConfig().has(EXACT_CHECK) ? AntiRageAddon.getInstance().getConfig().get(EXACT_CHECK).getAsBoolean() : false;
        this.toggleKey = AntiRageAddon.getInstance().getConfig().has(TOGGLE_KEY) ? AntiRageAddon.getInstance().getConfig().get(TOGGLE_KEY).getAsInt() : Keyboard.KEY_U;
        //this.menuKey = AntiRageAddon.getInstance().getConfig().has(MENU_KEY) ? AntiRageAddon.getInstance().getConfig().get(MENU_KEY).getAsInt() : Keyboard.KEY_I;
    }

    private void createSettings(){
        this.subSettings.add(new HeaderElement("General"));

        this.subSettings.add(new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            enabled = aBoolean;
            saveConfig();
        }, enabled));


        this.subSettings.add(new BooleanElement("Exact message check", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            exact = aBoolean;
            saveConfig();
        }, exact));

        KeyElement keyElement = new KeyElement( "Disable filter for 15 seconds",
            new ControlElement.IconData( Material.COAL ),
                toggleKey, newKey -> {
                if(newKey == toggleKey){
                    return;
                }
                toggleKey = newKey;
                saveConfig();
            });
        this.subSettings.add( keyElement );

        /*keyElement = new KeyElement( "Edit list",
            new ControlElement.IconData( Material.PAPER ),
                menuKey, newKey -> {
                if(newKey == menuKey){
                    return;
                }
                menuKey = newKey;
                saveConfig();
            });
        this.subSettings.add( keyElement );*/
    }

    private void saveConfig(){
        AntiRageAddon.getInstance().getConfig().addProperty(ENABLED, this.enabled);
        AntiRageAddon.getInstance().getConfig().addProperty(EXACT_CHECK, this.exact);
        AntiRageAddon.getInstance().getConfig().addProperty(TOGGLE_KEY, this.toggleKey);
        //AntiRageAddon.getInstance().getConfig().addProperty(MENU_KEY, this.menuKey);
    }

    public boolean isEnabled(){
        return this.enabled && !toDisabled;
    }

    private boolean toDisabled = false;
    public void setTODisabled(boolean enabled){
        this.toDisabled = enabled;
    }
    public boolean isExactCheck(){
        return this.exact;
    }


    public int getToggleKey(){
        return this.toggleKey;
    }

    /*public int getMenuKey(){
        return this.menuKey;
    }*/
}
