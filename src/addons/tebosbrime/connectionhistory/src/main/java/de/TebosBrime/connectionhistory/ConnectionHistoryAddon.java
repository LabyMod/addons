package de.TebosBrime.connectionhistory;

import de.TebosBrime.connectionhistory.gui.ConnectionHistoryGUI;
import de.TebosBrime.connectionhistory.listener.ServerJoinListener;
import de.TebosBrime.connectionhistory.listener.ServerQuitListener;
import net.labymod.api.LabyModAddon;
import net.labymod.gui.elements.Tabs;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class ConnectionHistoryAddon extends LabyModAddon {

    private static ConnectionHistoryAddon connectionHistoryAddon;
    private ServerDataManager serverDataManager;

    @Override
    public void onEnable() {

        System.out.println("============================================");
        System.out.println("Activate ConnectionHistory Addon for LabyMod");
        System.out.println("============================================");

        connectionHistoryAddon = this;

        serverDataManager = new ServerDataManager();

        new ServerJoinListener();
        new ServerQuitListener();

        Tabs.getTabUpdateListener().add(map -> map.put("Connection History", new Class[]{ConnectionHistoryGUI.class}));


        Runtime.getRuntime().addShutdownHook(new Thread(() -> serverDataManager.shutdown()));

        System.out.println("============================================");
        System.out.println("Enabled ConnectionHistory Addon for LabyMod ");
        System.out.println("============================================");

    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    public static ConnectionHistoryAddon getInstance(){
        return connectionHistoryAddon;
    }

    public ServerDataManager getServerDataManager(){
        return serverDataManager;
    }
}
