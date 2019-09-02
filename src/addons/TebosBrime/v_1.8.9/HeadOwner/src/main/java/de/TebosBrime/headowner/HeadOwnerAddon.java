package de.TebosBrime.headowner;

import de.TebosBrime.headowner.elements.HeadModule;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class HeadOwnerAddon extends LabyModAddon {

    public static HeadOwnerAddon headOwnerAddon;
    public static Settings settings;
    public static Listener listener;

    @Override
    public void onEnable() {
        System.out.println("============================================");
        System.out.println("    Activate HeadOwner Addon for LabyMod    ");
        System.out.println("============================================");

        headOwnerAddon = this;
        listener = new Listener();
        this.getApi().registerModule(new HeadModule());

        System.out.println("============================================");
        System.out.println("    Enabled HeadOwner Addon for LabyMod     ");
        System.out.println("============================================");
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        settings = new Settings(list);
    }

    public static HeadOwnerAddon getInstance(){
        return headOwnerAddon;
    }

    public static Settings getSettings() {
        return settings;
    }
}
