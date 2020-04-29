package de.raik.directconnecthistory;

import net.labymod.gui.ModGuiScreenServerList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOpenListener {

    // Variable where the multiplayer screen will be saved
    private GuiMultiplayer multiplayerScreen = null;

    /*
     * Event which is needed to detect the guis
     * It scans the multiplayer gui
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onDraw(GuiScreenEvent.DrawScreenEvent.Post event) {
        //Breaking when the gui isn't a direct connect gui
        if (!(event.gui instanceof GuiScreenServerList) && !(event.gui instanceof ModGuiScreenServerList)) {
            //Setting Multiplayer gui if the gui is one
            if (event.gui instanceof GuiMultiplayer)
                this.multiplayerScreen = (GuiMultiplayer) event.gui;

            return;
        }

        /*
         * Opening edited direct connect gui if the multiplayer gui isn't null
         * the gui is needed in the gui
         */
        if (this.multiplayerScreen != null)
            Minecraft.getMinecraft().displayGuiScreen(new DirectConnectScreen(this.multiplayerScreen, new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "", false)));
    }

}
