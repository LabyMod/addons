package de.raik.directconnecthistory;

import net.labymod.gui.ModGuiScreenServerList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
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
        //Getting gui screen
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        //Breaking when the gui isn't a direct connect gui
        if (!(gui instanceof GuiScreenServerList) && !(gui instanceof ModGuiScreenServerList)) {
            //Setting multiplayer gui if the gui is one
            if (gui instanceof GuiMultiplayer)
                this.multiplayerScreen = (GuiMultiplayer) gui;

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
