package de.raik.friendtags.serverinteraction;

import de.raik.friendtags.FriendTagsAddon;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

/**
 * Listener to catch
 * disconnect for resetting
 * allowed value
 *
 * @see net.labymod.utils.Consumer
 *
 * @author Raik
 * @version 1.0
 */
public class ReEnablingOnQuitListener implements Consumer<ServerData> {

    /**
     * Addon instance for enabling the addon
     */
    private final FriendTagsAddon addon;

    /**
     * Constructor for setting the addon instance
     *
     * @param addon The addon instance
     */
    public ReEnablingOnQuitListener(FriendTagsAddon addon) {
        this.addon = addon;
    }

    /**
     * Listener method to handle quit
     *
     * @param serverData The serverData of the server before
     */
    @Override
    public void accept(ServerData serverData) {
        this.addon.setAllowed(true);
    }
}
