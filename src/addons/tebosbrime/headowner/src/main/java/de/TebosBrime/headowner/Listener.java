package de.TebosBrime.headowner;

import de.TebosBrime.headowner.utils.utils;
import net.labymod.main.LabyMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Listener {

    public Listener(){
        HeadOwnerAddon.getInstance().getApi().registerForgeListener(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        // copy properties
        if(HeadOwnerAddon.getSettings() == null || HeadOwnerAddon.getSettings().getCopyKey() == -1){
            return;
        }

        if (Keyboard.isKeyDown(HeadOwnerAddon.getSettings().getCopyKey())) {
            if (HeadOwnerAddon.getSettings().isEnabled()) {
                utils.Skull skull = utils.getSkullLooking();
                String name = skull.getCopy();

                StringSelection stringSelection = new StringSelection(name);
                try {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                } catch (IllegalStateException ex){
                    Thread t = new Thread(() -> {
                        try {
                            wait(100);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(stringSelection, null);
                        } catch (IllegalStateException | InterruptedException ex2){
                            ex2.printStackTrace();
                            LabyMod.getInstance().getLabyModAPI().displayMessageInChat("[HeadOwner] Can't modify clipboard :/");
                        }
                    });
                    t.start();
                }
            }
        }
    }
}
