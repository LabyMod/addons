package de.TebosBrime.connectionhistory.util;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerData implements Serializable {

    private String name;
    private ArrayList<ServerDataConnection> connections;

    private ServerDataConnection current;
    private long totalTime;

    public ServerData(String name, ArrayList<ServerDataConnection> serverDataConnections){

        this.name = name;
        this.connections = serverDataConnections;

        serverDataConnections.forEach(serverDataConnection -> totalTime += serverDataConnection.getTimePlayed());

    }

    public void join(){

        current = new ServerDataConnection(System.currentTimeMillis());
        connections.add(current);

    }

    public void quit(){

        if(current == null || current.isCompleted()){
            return;
        }

        current.setEnd(System.currentTimeMillis());
        totalTime += current.getTimePlayed();
    }

    public ArrayList<ServerDataConnection> getConnections(){

        return connections;

    }


    public String getFullServerName(){

        return name;

    }

    public String getServerName(){

        // without the default port
        return name.replace(":25565", "");

    }


    public long getTotalTime(){

        return totalTime;

    }

}
