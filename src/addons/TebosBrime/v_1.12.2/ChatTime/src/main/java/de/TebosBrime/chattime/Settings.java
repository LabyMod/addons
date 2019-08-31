package de.TebosBrime.chattime;

import net.labymod.settings.elements.*;
import net.labymod.utils.Material;

import java.util.List;

public class Settings {

    private boolean enabled;
    private String chatData;
    private String chatData2;
    private boolean before;
    private String hoverTime;

    Settings(List<SettingsElement> subSettings){
        loadConfig();
        subSettings.add(new HeaderElement("General"));

        subSettings.add( new BooleanElement( "Enabled", new ControlElement.IconData( Material.LEVER ), aBoolean -> {
            enabled = aBoolean;
            saveConfig();
        }, enabled) );

        StringElement channelStringElement = new StringElement( "Time Formatting", new ControlElement.IconData( Material.PAPER ),
                chatData, chatDataString -> {
                    chatData = chatDataString;
                    saveConfig();
                });
        subSettings.add( channelStringElement );

        channelStringElement = new StringElement( "Prefix Formatting", new ControlElement.IconData( Material.PAPER ),
                chatData2, chatDataString -> {
                    chatData2 = chatDataString;
                    saveConfig();
                });
        subSettings.add( channelStringElement );

        channelStringElement = new StringElement( "Hover Formatting", new ControlElement.IconData( Material.PAPER ),
                hoverTime, chatDataString -> {
                    hoverTime = chatDataString;
                    saveConfig();
                });
        subSettings.add( channelStringElement );

        subSettings.add( new BooleanElement( "Before Message", new ControlElement.IconData( Material.LEVER ), aBoolean -> {
            before = aBoolean;
            saveConfig();
        }, before) );

    }

    private final String ENABLED = "enabled";
    private final String CHATDATA = "chatData";
    private final String CHATDATA2 = "chatData2";
    private final String BEFORE = "before";
    private final String HOVER = "hover";

    private void loadConfig(){
        this.enabled = ChatTimeAddon.getInstance().getConfig().has(ENABLED) ? ChatTimeAddon.getInstance().getConfig().get( ENABLED ).getAsBoolean() : true;
        this.chatData = ChatTimeAddon.getInstance().getConfig().has( CHATDATA ) ? ChatTimeAddon.getInstance().getConfig().get( CHATDATA ).getAsString() : "HH:mm:ss";
        this.chatData2 = ChatTimeAddon.getInstance().getConfig().has( CHATDATA2 ) ? ChatTimeAddon.getInstance().getConfig().get( CHATDATA2 ).getAsString() : "&4[&e%time%&4] ";
        this.before = ChatTimeAddon.getInstance().getConfig().has(BEFORE) ? ChatTimeAddon.getInstance().getConfig().get( BEFORE ).getAsBoolean() : true;
        this.hoverTime = ChatTimeAddon.getInstance().getConfig().has( HOVER ) ? ChatTimeAddon.getInstance().getConfig().get( HOVER ).getAsString() : "";
    }

    private void saveConfig(){
        ChatTimeAddon.getInstance().getConfig().addProperty(ENABLED, this.enabled);
        ChatTimeAddon.getInstance().getConfig().addProperty(CHATDATA, this.chatData);
        ChatTimeAddon.getInstance().getConfig().addProperty(CHATDATA2, this.chatData2);
        ChatTimeAddon.getInstance().getConfig().addProperty(BEFORE, this.before);
        ChatTimeAddon.getInstance().getConfig().addProperty(HOVER, this.hoverTime);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getChatData(){
        return chatData;
    }

    public String getChatData2() {
        return chatData2;
    }

    public boolean isBefore() {
        return before;
    }

    public String getHoverTime() {
        return hoverTime;
    }

    public boolean showHoverTime(){
        if(getHoverTime().length() == 0){
            return false;
        }
        return true;
    }
}
