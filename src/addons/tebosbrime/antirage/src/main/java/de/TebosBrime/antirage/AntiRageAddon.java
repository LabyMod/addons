package de.TebosBrime.antirage;

import de.TebosBrime.antirage.gui.FilterGui;
import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.Tabs;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class AntiRageAddon extends LabyModAddon {

    public static AntiRageAddon antiRageAddon;
    public static Settings settings;
    public static Listener listener;
    public static Filter filter;

    @Override
    public void onEnable() {
        System.out.println("============================================");
        System.out.println("    Activate AntiRage Addon for LabyMod     ");
        System.out.println("============================================");

        antiRageAddon = this;
        filter = new Filter();
        listener = new Listener();

        Tabs.getTabUpdateListener().add(map -> map.put("AntiRage filterlist", new Class[]{FilterGui.class}));

        System.out.println("============================================");
        System.out.println("    Enabled AntiRage Addon for LabyMod      ");
        System.out.println("============================================");
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        settings = new Settings(list);
    }

    public static AntiRageAddon getInstance(){
        return antiRageAddon;
    }

    public static Settings getSettings(){
        return settings;
    }

    public static List<String> getFilter(){
        return filter.getFilter();
    }

    public static Filter getFilterClass(){
        return filter;
    }
}
