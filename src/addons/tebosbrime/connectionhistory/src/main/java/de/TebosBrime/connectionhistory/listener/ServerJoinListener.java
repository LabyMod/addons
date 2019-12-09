package de.TebosBrime.connectionhistory.listener;

import com.google.gson.JsonObject;
import de.TebosBrime.connectionhistory.ConnectionHistoryAddon;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

public class ServerJoinListener implements Consumer<ServerData> {

    public ServerJoinListener(){
        ConnectionHistoryAddon.getInstance().getApi().getEventManager().registerOnJoin(this);
    }

    @Override
    public void accept(ServerData serverData) {
        String serverName = serverData.getIp() + ":" + serverData.getPort();
        ConnectionHistoryAddon.getInstance().getServerDataManager().join(serverName);
    }

}
