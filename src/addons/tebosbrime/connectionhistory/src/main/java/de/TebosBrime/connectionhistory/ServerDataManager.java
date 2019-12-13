package de.TebosBrime.connectionhistory;

import com.google.gson.*;
import de.TebosBrime.connectionhistory.util.ServerData;
import de.TebosBrime.connectionhistory.util.ServerDataConnection;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.*;

public class ServerDataManager {

    private HashMap<String, ServerData> dataHashMap;
    private File file;
    private String currentServer;
    private String lastServer;

    protected ServerDataManager() {

        file = new File( Minecraft.getMinecraft().mcDataDir + "\\LabyMod\\CONNECTION_HISTORY_Data.json");
        currentServer = null;

        try{
            loadData();
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("error while load connection data..");
        }

    }

    public void join(String serverName){

        serverName = serverName.toUpperCase();

        if(serverName.equals(currentServer)){
            return;
        }

        currentServer = serverName;
        if(!dataHashMap.containsKey(serverName)){
            dataHashMap.put(serverName, new ServerData(serverName, new ArrayList<>()));
        }

        dataHashMap.get(serverName).join();

    }

    public void quit(String serverName){

        serverName = serverName.toUpperCase();

        if(!dataHashMap.containsKey(serverName)){
            dataHashMap.put(serverName, new ServerData(serverName, new ArrayList<>()));
        }

        dataHashMap.get(serverName).quit();
        lastServer = currentServer;

        currentServer = null;

        saveData();
    }

    public ServerData getServerData(String serverName){

        serverName = serverName.toUpperCase();

        if(!dataHashMap.containsKey(serverName)){
            dataHashMap.put(serverName, new ServerData(serverName, new ArrayList<>()));
        }

        return dataHashMap.get(serverName);

    }

    public Set<String> getAllServerNames(){

        return dataHashMap.keySet();

    }

    public ArrayList<ServerData> getAllServerData(){

        ArrayList<ServerData> result = new ArrayList<>();

        List<String> serverNames = new ArrayList<>(getAllServerNames());
        Collections.sort(serverNames);

        for(String serverName : serverNames){
            result.add(dataHashMap.get(serverName));
        }

        return result;

    }

    public String getLastServer(){

        return lastServer;

    }

    private void loadData() throws IOException {

        dataHashMap = new HashMap<>();

        if(file.exists()){

            JsonParser jsonParser = new JsonParser();
            FileReader reader = new FileReader(file);

            Object obj = jsonParser.parse(reader);
            JsonObject serverList = (JsonObject) obj;

            for (Map.Entry<String, JsonElement> entrySet : serverList.entrySet()) {

                String serverName = entrySet.getKey();
                JsonArray connections = entrySet.getValue().getAsJsonArray();

                ArrayList<ServerDataConnection> serverDataConnections = new ArrayList<>();

                for(JsonElement conn : connections){

                    String data = conn.getAsString();
                    ServerDataConnection serverDataConnection = new ServerDataConnection(data);
                    serverDataConnections.add(serverDataConnection);

                }

                ServerData serverData = new ServerData(serverName, serverDataConnections);
                dataHashMap.put(serverName, serverData);
            }
        }

        System.out.println("[Connection History] loaded: " + dataHashMap.size() + " server");
    }

    private void saveData() {

        Gson gson = new GsonBuilder().create();

        JsonObject obj = new JsonObject();
        dataHashMap.forEach((key, value) -> {

            ArrayList<String> arr = new ArrayList<>();
            value.getConnections().forEach((con) -> arr.add(con.getData()));

            JsonArray array = gson.toJsonTree(arr).getAsJsonArray();
            obj.add(key, array);

        });

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(gson.toJson(obj));

            writer.close();

            System.out.println("[Connection History] saved: " + dataHashMap.size() + " server");

        }catch (IOException e){
            e.printStackTrace();
            System.err.println("error while save connection data..");
        }

    }

    public void shutdown() {

        if(currentServer != null){

            quit(currentServer);

        }else{

            saveData();

        }

    }
}
