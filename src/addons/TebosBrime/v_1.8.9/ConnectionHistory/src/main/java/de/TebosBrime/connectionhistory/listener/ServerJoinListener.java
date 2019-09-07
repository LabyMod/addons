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

        sendLastServer();
    }

    private void sendLastServer(){

        String lastServer = ConnectionHistoryAddon.getInstance().getServerDataManager().getLastServer();
        String serverName = null;
        int serverPort = -1;

        if(lastServer != null){
            String[] list = lastServer.split(":");
            serverName = list[0];
            serverPort = Integer.valueOf(list[1]);
        }

        JsonObject obj = new JsonObject();
        obj.addProperty("serverName", serverName);
        obj.addProperty("serverPort", serverPort);

        ConnectionHistoryAddon.getInstance().getApi().sendJsonMessageToServer("ls", obj);

    }
}
