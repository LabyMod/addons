package de.raik.directconnecthistory;

import net.labymod.core.LabyModCore;
import net.labymod.core.ServerPingerData;
import net.labymod.gui.elements.DropDownMenu;
import net.labymod.main.LabyMod;
import net.labymod.utils.Consumer;
import net.labymod.utils.DrawUtils;
import net.labymod.utils.ModColor;
import net.labymod.utils.manager.ServerInfoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/*
 * Creating own direct connect screen
 * to implement the needed features
 */
@SideOnly(Side.CLIENT)
public class DirectConnectScreen extends GuiScreen {

    //Default attributes for direct connect screen
    private GuiMultiplayer guiBefore;
    private ServerData serverData;
    private GuiTextField textField;
    private long lastUpdate = 0L;
    private long updateCooldown = 2000L;
    private ServerInfoRenderer serverInfoRenderer;

    //Own needed attributes
    private DropDownMenu<String> lastServers;
    private String lastServer;
    private boolean toSmall = false;

    /*
     * Copied default constructor
     */
    public DirectConnectScreen(GuiMultiplayer gui, ServerData serverData) {
        this.guiBefore = gui;
        this.serverData = serverData;
    }

    /*
     * Copied from ModGuiScreenServerList
     */

    public void updateScreen() {
        this.textField.updateCursorCounter();
        if (LabyMod.getSettings().directConnectInfo && !this.textField.getText().replace(" ", "").isEmpty()) {
            if (this.lastUpdate + this.updateCooldown < System.currentTimeMillis()) {
                this.lastUpdate = System.currentTimeMillis();
                LabyModCore.getServerPinger().pingServer((ExecutorService)null, this.lastUpdate, this.textField.getText(), new Consumer<ServerPingerData>() {
                    public void accept(ServerPingerData accepted) {
                        if (accepted == null || accepted.getTimePinged() == DirectConnectScreen.this.lastUpdate) {
                            DirectConnectScreen.this.serverInfoRenderer = new ServerInfoRenderer(DirectConnectScreen.this.textField.getText(), accepted);
                        }
                    }
                });
            }
        } else {
            this.serverInfoRenderer = new ServerInfoRenderer(this.textField.getText(), (ServerPingerData)null);
            this.lastUpdate = -1L;
        }
    }

    /*
     * modified default initGui method
     */
    public void initGui() {
        //Default element adding
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        //Preventing overlapping
        this.toSmall = this.height / 4 + 96 + 12 < 220;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, toSmall ? 195 : this.height / 4 + 96 + 12, I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, toSmall ? 216 : this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        this.textField = new GuiTextField(2, LabyModCore.getMinecraft().getFontRenderer(), this.width / 2 - 100, 116 - (toSmall ? 19 : 0), 200, 20);
        this.textField.setMaxStringLength(128);
        this.textField.setFocused(true);
        this.textField.setText(this.mc.gameSettings.lastServer);
        //Setting last server
        this.lastServer = this.textField.getText();
        //Change status of Join Button (default)
        ((GuiButton)this.buttonList.get(0)).enabled = this.textField.getText().length() > 0 && this.textField.getText().split(":").length > 0;
        // Setting up last servers drop down
        ArrayList<String> servers = (ArrayList<String>) DirectConnectHistoryAddon.getInstance().getServers().clone();
        while (servers.size() > DirectConnectHistoryAddon.getInstance().getHistoryAmount()) {
            servers.remove(servers.size() - 1);
        }
        String[] serverArray = new String[servers.size()];
        this.lastServers = (new DropDownMenu<String>("Server history", this.width / 2 - 100, 160 - (toSmall ? 19 : 0), 200, 20)).fill(servers.toArray(serverArray));
        if (servers.toArray(serverArray).length >= 1)
            this.lastServers.setSelected(servers.toArray(serverArray)[0]);
        this.lastServers.setMaxY(this.height);
        this.lastServers.setEntryDrawer(new DropDownMenu.DropDownEntryDrawer() {
            @Override
            public void draw(Object object, int x, int y, String s) {
                LabyMod.getInstance().getDrawUtils().drawString((String) object, (double)x, (double)y);
            }
        });
        this.lastServers.setEnabled(true);
        // Adding new buttons
        this.buttonList.add(new GuiButton(3, this.width / 2- 100, 190 - (toSmall ? 19 : 0), 100, 20, "Paste"));
        this.buttonList.get(2).enabled = servers.size() > 0;
        this.buttonList.add(new GuiButton(4, this.width / 2, 190 - (toSmall ? 19 : 0), 100, 20, "Join"));
        this.buttonList.get(3).enabled = servers.size() > 0;
    }

    /*
     * Edited default onGuiClosed() method
     */
    public void onGuiClosed() {
        // Default things
        Keyboard.enableRepeatEvents(false);
        this.mc.gameSettings.lastServer = this.textField.getText();
        this.mc.gameSettings.saveOptions();
        LabyModCore.getServerPinger().closePendingConnections();
        //Calling server list changing
        this.changeList(this.textField.getText());
    }

    /*
     * Changing history and setting to global Addon instance
     */
    private void changeList(String ip) {
        // Check if a changing is neeeded
        if (ip.equalsIgnoreCase(this.lastServer))
            return;
        //Editing server history
        ArrayList<String> servers = (ArrayList<String>) DirectConnectHistoryAddon.getInstance().getServers().clone();
        if (servers.contains(this.lastServer))
            servers.remove(this.lastServer);
        if (servers.contains(ip))
            servers.remove(ip);
        if (!this.lastServer.equals(""))
            servers.add(0, this.lastServer);
        while (servers.size() > DirectConnectHistoryAddon.getInstance().getHistoryAmount()) {
            servers.remove(servers.size() - 1);
        }
        // Setting history
        DirectConnectHistoryAddon.getInstance().setServers(servers, ip);
    }

    /*
     * Connect to server method
     */
    private void connect(String ip) {
        if (LabyModCore.getMinecraft().getWorld() != null) {
            LabyModCore.getMinecraft().getWorld().sendQuittingDisconnectingPacket();
            Minecraft.getMinecraft().loadWorld((WorldClient)null);
        }
        LabyMod.getInstance().connectToServer(ip);
    }

    /*
     * edited function to handle button click
     * partly copied
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        //Only execute when button is enabled and dropdown is close to prevent errors
        if (button.enabled && !this.lastServers.isOpen()) {
            //Default cancel action
            if (button.id == 1) {
                this.guiBefore.confirmClicked(false, 0);
            // Connect Button
            } else if (button.id == 0) {
                // Setting server id
                this.serverData.serverIP = this.textField.getText();
                // Connect to server
                this.connect(this.serverData.serverIP);
            // Paste Button
            } else if (button.id == 3) {
                //Filling selected history server into text field
                this.textField.setText((String)lastServers.getSelected());
            // Join Button
            } else if (button.id == 4) {
                //Filling selected history server into text field
                this.textField.setText(this.lastServers.getSelected());
                // Connect to server
                this.connect(this.lastServers.getSelected());
            }
        }

    }

    /*
     * default key typed method
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.textField.textboxKeyTyped(typedChar, keyCode)) {
            ((GuiButton)this.buttonList.get(0)).enabled = this.textField.getText().length() > 0 && this.textField.getText().split(":").length > 0;
        } else if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

    }

    /*
     * edited default mouseClicked Method
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        // Default mouse click callings
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
        // Calling dropdown onclick
        this.lastServers.onClick(mouseX, mouseY, mouseButton);
    }

    /*
     * edited default drawscreen method
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //Default drawings
        this.drawDefaultBackground();
        this.drawCenteredString(LabyModCore.getMinecraft().getFontRenderer(), I18n.format("selectServer.direct", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(LabyModCore.getMinecraft().getFontRenderer(), I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 100 - (toSmall ? 19 : 0), 10526880);
        this.textField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
        //Drawing DropDownMenu
        this.lastServers.draw(mouseX, mouseY);
        //Drawing Server Info
        if (this.serverInfoRenderer != null && this.lastUpdate != -1L) {
            DrawUtils drawUtils = LabyMod.getInstance().getDrawUtils();
            int leftBound = this.width / 2 - 150;
            int rightBound = this.width / 2 + 150;
            int posY = 44;
            int height = 30;
            drawUtils.drawRectangle(leftBound, posY - 4, rightBound, posY + 6 + height, -2147483648);
            this.serverInfoRenderer.drawEntry(leftBound + 3, posY, rightBound - leftBound, mouseX, mouseY);
            int stateColorR = this.serverInfoRenderer.canReachServer() ? 105 : 240;
            int stateColorG = this.serverInfoRenderer.canReachServer() ? 240 : 105;
            int stateColorB = 105;
            double total = (double)(rightBound - leftBound);
            double barPercent = total / (double)this.updateCooldown * (double)(System.currentTimeMillis() - this.lastUpdate);
            if (barPercent > total) {
                barPercent = total;
            }

            int colorPercent = (int)Math.round(155.0D / (double)this.updateCooldown * (double)(System.currentTimeMillis() - this.lastUpdate - 100L));
            drawUtils.drawRectangle(leftBound, posY - 6, rightBound, posY - 5, -2147483648);
            drawUtils.drawRectangle(leftBound, posY - 6, rightBound, posY - 5, ModColor.toRGB(stateColorR, stateColorG, stateColorB, 155 - colorPercent));
            drawUtils.drawRect((double)leftBound, (double)(posY - 6), (double)leftBound + barPercent, (double)(posY - 5), ModColor.toRGB(stateColorR, stateColorG, stateColorB, 150));
            drawUtils.drawGradientShadowTop((double)(posY - 4), (double)leftBound, (double)rightBound);
            drawUtils.drawGradientShadowBottom((double)(posY + 6 + height), (double)leftBound, (double)rightBound);
        }
    }



}
