package de.TebosBrime.chattime;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class ChatTimeAddon extends LabyModAddon {

    private static ChatTimeAddon chatTimeAddon;
    private static Settings settings;
    private static Listener listener;

    @Override
    public void onEnable() {
        System.out.println("======================================");
        System.out.println(" Activate Chat Time Addon for LabyMod ");
        System.out.println("======================================");

        chatTimeAddon = this;
        listener = new Listener();

        System.out.println("======================================");
        System.out.println(" Enabled Chat Time Addon for LabyMod  ");
        System.out.println("======================================");
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        settings = new Settings(list);
    }

    public static ChatTimeAddon getInstance(){
        return chatTimeAddon;
    }

    public static Settings getSettings(){
        return settings;
    }
}
