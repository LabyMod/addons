package de.TebosBrime.headowner;

import net.labymod.settings.elements.*;
import net.labymod.utils.Material;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Settings {

    private boolean enabled;
    private int length;
    private int copyKey;

    List<SettingsElement> subSettings;

    Settings(List<SettingsElement> subSettings){
        this.subSettings = subSettings;

        loadConfig();
        createSettings();
    }

    private final String ENABLED = "enabled";
    private final String LENGHT = "length";
    private final String COPYKEY = "copyKey";

    private void loadConfig(){
        this.enabled = HeadOwnerAddon.getInstance().getConfig().has(ENABLED) ? HeadOwnerAddon.getInstance().getConfig().get(ENABLED).getAsBoolean() : true;
        this.length = HeadOwnerAddon.getInstance().getConfig().has(LENGHT) ? HeadOwnerAddon.getInstance().getConfig().get(LENGHT).getAsInt() : 10;
        this.copyKey = HeadOwnerAddon.getInstance().getConfig().has(COPYKEY) ? HeadOwnerAddon.getInstance().getConfig().get(COPYKEY).getAsInt() : Keyboard.KEY_H;
    }

    private void createSettings(){
        this.subSettings.add(new HeaderElement("General"));
        this.subSettings.add(new BooleanElement("Enabled", new ControlElement.IconData(Material.LEVER), aBoolean -> {
            enabled = aBoolean;
            saveConfig();
        }, enabled));

        NumberElement distanceElement = new NumberElement( "Block reach distance", new ControlElement.IconData( Material.MAP ), length);
        distanceElement.addCallback(integer -> {
            length = integer;
            saveConfig();
        });
        this.subSettings.add(distanceElement);

        KeyElement keyElement = new KeyElement( "Copy head data to clipboard", new ControlElement.IconData(Material.COAL), copyKey, newKey -> {
            copyKey = newKey;
            saveConfig();
        });
        this.subSettings.add( keyElement );
    }

    private void saveConfig(){
        HeadOwnerAddon.getInstance().getConfig().addProperty(ENABLED, this.enabled);
        HeadOwnerAddon.getInstance().getConfig().addProperty(LENGHT,  this.length);
        HeadOwnerAddon.getInstance().getConfig().addProperty(COPYKEY, this.copyKey);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getLength(){
        return length;
    }

    public int getCopyKey() {
        return copyKey;
    }
}
