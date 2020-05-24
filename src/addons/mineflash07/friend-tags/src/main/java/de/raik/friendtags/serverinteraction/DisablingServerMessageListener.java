package de.raik.friendtags.serverinteraction;

import com.google.gson.JsonElement;
import de.raik.friendtags.FriendTagsAddon;
import net.labymod.api.events.ServerMessageEvent;
import net.labymod.main.LabyMod;

/**
 * Listener to catch server messages
 * which change the enable status of the addon
 * @see net.labymod.api.events.ServerMessageEvent
 *
 * @author Raik
 * @version 1.0
 */
public class DisablingServerMessageListener implements ServerMessageEvent {

    /**
     * Addon instance for disabling the addon
     */
    private final FriendTagsAddon addon;

    /**
     * Constructor for setting the addon instance
     *
     * @param addon The addon instance
     */
    public DisablingServerMessageListener(FriendTagsAddon addon) {
        this.addon = addon;
    }

    /**
     * The listener method for changing the enable status
     *
     * @param messageKey The messageKey of the server message
     * @param jsonElement The json response of the server message
     */
    @Override
    public void onServerMessage(String messageKey, JsonElement jsonElement) {
        //Cancel if wrong message key
        if (!messageKey.equals("advancedfriendtags-enabled"))
            return;

        boolean enabled = jsonElement.getAsJsonObject().get("enabled").getAsBoolean();

        //Setting value
        this.addon.setAllowed(enabled);

        //Sending status message
        LabyMod.getInstance().displayMessageInChat(enabled ? "§aThe server enabled advanced friend tags."
                : "§cThe server disabled advanced friend tags.");
    }
}
