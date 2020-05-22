package de.raik.friendtags;

import com.google.gson.JsonObject;
import de.raik.friendtags.serverinteraction.DisablingServerMessageListener;
import de.raik.friendtags.serverinteraction.ReEnablingOnQuitListener;
import net.labymod.api.EventManager;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.*;
import net.labymod.utils.Material;
import net.labymod.utils.UUIDFetcher;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Addon class
 *
 * @author Raik
 * @version 1.0
 */
public class FriendTagsAddon extends LabyModAddon {

    /**
     * Represents the current enable status of the addon
     * Default value true
     */
    private boolean enabled = true;

    /**
     * The format of the friend tag
     */
    private String friendFormat = "&lFriend";

    /**
     * Represents if the server allows the addon
     */
    private boolean allowed = true;

    /**
     * Addon method to enable the
     * addon features
     */
    @Override
    public void onEnable() {
        EventManager eventManager = getApi().getEventManager();

        //Registering the render event
        eventManager.register(new NameTagRenderer(this));

        //Registering Server Interaction Listener
        eventManager.register(new DisablingServerMessageListener(this));
        eventManager.registerOnQuit(new ReEnablingOnQuitListener(this));
    }

    /**
     * Addon method to load the config
     * Addon settings will be set to default
     * if no exists
     */
    @Override
    public void loadConfig() {
        JsonObject labyModAddonConfig = getConfig();

        //Enabled setting
        this.enabled = labyModAddonConfig.has("enabled") ?
                labyModAddonConfig.get("enabled").getAsBoolean() : this.enabled;

        //Friend tag
        this.friendFormat = labyModAddonConfig.has("friendformat") ? labyModAddonConfig.get("friendformat")
                .getAsString() : this.friendFormat;
    }

    /**
     * Addon method to fill the settings
     *
     * @param settings The list the settings have to be added to
     */
    @Override
    protected void fillSettings(List<SettingsElement> settings) {
        //Enabled setting
        settings.add(new BooleanElement("Enabled", this, new ControlElement.IconData(Material.LEVER)
                , "enabled", this.enabled));

        //Friend tag
        settings.add(new StringElement("Tag Format", this, new ControlElement.IconData(
                "labymod/addons/advancedfriendtags/tagformat.png"), "friendformat", this.friendFormat));
    }

    /**
     * Get enabled value
     *
     * @return The value
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Get the chat format
     *
     * @return the value
     */
    public String getFriendFormat() {
        return this.friendFormat;
    }

    /**
     * Get the allowed value
     *
     * @return The value
     */
    public boolean isAllowed() {
        return allowed;
    }

    /**
     * Set the allowed value
     * on change
     *
     * @param allowed The new value
     */
    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}
