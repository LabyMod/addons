package de.raik.directconnecthistory;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DirectConnectHistoryAddon extends LabyModAddon {

    //Addon settings
    private int historyAmount = 5;
    private ArrayList<String> server = new ArrayList<>();
    private Config config;

    //Singleton variable
    private static DirectConnectHistoryAddon instance;

    @Override
    public void onEnable() {
        //Registering event
        getApi().registerForgeListener(new GuiOpenListener());

        //Setting settings
        this.config = new Config("Direct Connect History", Paths.get(Minecraft.getMinecraft().mcDataDir.toString()
                ,"LabyMod", "addons-config-global").toString());

        //Calling direct connect check
        this.checkDirectConnectServer();

        // Setting static instance
        instance = this;
    }

    @Override
    public void loadConfig() {
        this.historyAmount = config.getConfigAsJsonObject().has("amount") ?
                config.getConfigAsJsonObject().get("amount").getAsInt() : this.historyAmount;
        if (!config.getConfigAsJsonObject().has("servers")) {
            config.getConfigAsJsonObject().add("servers", new JsonArray());
            return;
        }
        this.server.clear();
        JsonArray array = config.getConfigAsJsonObject().get("servers").getAsJsonArray();
        for (int i = 0; i < Math.min(this.historyAmount, array.size()); i++) {
            this.server.add(array.get(i).getAsString());
        }
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        //Creating configuration element
        MinNumberElement lengthElement = new MinNumberElement("Length of history", "amount",
                new ControlElement.IconData(Material.BOOK), 1, this.historyAmount);
        lengthElement.addCallback(new Consumer<Integer>() {
            @Override
            public void accept(Integer accepted) {
                historyAmount = accepted;
                config.getConfigAsJsonObject().addProperty("amount", accepted);
                config.save();
            }
        });

        //Adding configuration element
        list.add(lengthElement);
    }

    //Attribute Getter

    public int getHistoryAmount() {
        return historyAmount;
    }

    public ArrayList<String> getServers() {
        return this.server;
    }

    /*
     * Checking if the direct connect server affects
     * the list
     * maybe it has been changed without the addon
     */
    private void checkDirectConnectServer() {
        ArrayList<String> newServerList = new ArrayList<>(this.server);
        String directConnectServer = Minecraft.getMinecraft().gameSettings.lastServer;
        String lastServerOfAddon = this.config.getConfigAsJsonObject().get("lastServer").getAsString();

        if (directConnectServer.equals(lastServerOfAddon))
            return;

        newServerList.remove(directConnectServer);
        newServerList.remove(lastServerOfAddon);
        newServerList.add(0, lastServerOfAddon);
        while(newServerList.size() > this.historyAmount) {
            newServerList.remove(newServerList.size() - 1);
        }

        newServerList.remove(directConnectServer);
        this.setServers(newServerList, directConnectServer);
    }


    /*
     * Setting server list
     * Saving them in config file
     */
    public void setServers(ArrayList<String> servers, String enteredServer) {
        // set the list
        this.server = servers;

        //Creating config Json Element
        JsonArray jsonArray = new JsonArray();
        for (String server: servers) {
            jsonArray.add(new JsonPrimitive(server));
        }

        //Saving list in config
        config.getConfigAsJsonObject().add("servers", jsonArray);
        if (!enteredServer.equals(""))
            config.getConfigAsJsonObject().add("lastServer", new JsonPrimitive(enteredServer));
        this.config.save();
    }

    //Singelton getter
    public static DirectConnectHistoryAddon getInstance() {
        return instance;
    }
}
