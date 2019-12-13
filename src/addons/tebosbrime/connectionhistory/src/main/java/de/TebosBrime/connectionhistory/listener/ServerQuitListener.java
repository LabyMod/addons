package de.TebosBrime.connectionhistory.listener;

import de.TebosBrime.connectionhistory.ConnectionHistoryAddon;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

public class ServerQuitListener implements Consumer<ServerData> {

    public ServerQuitListener(){
        ConnectionHistoryAddon.getInstance().getApi().getEventManager().registerOnQuit(this);
    }

    @Override
    public void accept(ServerData serverData) {
        String serverName = serverData.getIp() + ":" + serverData.getPort();
        ConnectionHistoryAddon.getInstance().getServerDataManager().quit(serverName);
    }

}
