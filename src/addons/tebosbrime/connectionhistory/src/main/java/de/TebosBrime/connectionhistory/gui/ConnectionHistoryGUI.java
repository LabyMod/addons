package de.TebosBrime.connectionhistory.gui;

import de.TebosBrime.connectionhistory.ConnectionHistoryAddon;
import de.TebosBrime.connectionhistory.util.ServerData;
import de.TebosBrime.connectionhistory.util.TimeFormat;
import net.labymod.gui.elements.Tabs;
import net.labymod.main.LabyMod;
import net.labymod.utils.ModColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.labymod.gui.elements.Scrollbar;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionHistoryGUI extends GuiScreen {

    private Scrollbar scrollbar = new Scrollbar(26);
    private String selectedString = null;
    private String hoveredString = null;
    private GuiButton detailsButton;

    private ArrayList<ServerData> serverGuiElements;

    @Override
    public void initGui() {

        this.scrollbar.init();
        this.scrollbar.setPosition(this.width / 2 + 152, 44, this.width / 2 + 156, this.height - 32 - 3);

        detailsButton = new GuiButton(666, this.width / 2 - 37, this.height - 26, 100, 20, "Show details");
        this.buttonList.add(detailsButton);

        serverGuiElements = new ArrayList<>();
        ConnectionHistoryAddon.getInstance().getServerDataManager().getAllServerData().forEach((serverData) -> serverGuiElements.add(serverData));

        Tabs.initGuiScreen(this.buttonList, this);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button.id == 666) {
            openDetailsForServer(selectedString);
            return;
        }

        Tabs.actionPerformedButton(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(this.scrollbar.getScrollY());
        this.hoveredString = null;
        double yPos = 45.0D + this.scrollbar.getScrollY() + 5.0D;

        for (ServerData serverData : serverGuiElements){
            drawEntry(serverData, yPos, mouseX, mouseY);
            yPos += 26.0D;
        }
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(0, 41);
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(this.height - 32, this.height);

        LabyMod.getInstance().getDrawUtils().drawGradientShadowTop(41.0D, 0.0D, this.width);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowBottom(this.height - 32.0D, 0.0D, this.width);

        LabyMod.getInstance().getDrawUtils().drawCenteredString("Overview", this.width / 2, 29.0D);

        this.scrollbar.update(serverGuiElements.size());
        this.scrollbar.draw();

        this.detailsButton.enabled = (this.selectedString != null);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawEntry(ServerData serverData, double y, int mouseX, int mouseY) {

        int x = this.width / 2 - 150;

        boolean hovered = (mouseX > x) && (mouseX < x + 300) && (mouseY > y - 6) && (mouseY < y + 18) && (mouseX > 32) && (mouseY < this.height - 32);
        if (hovered) {
            this.hoveredString = serverData.getFullServerName();
        }

        int borderColor = (this.selectedString != null && this.selectedString.equals(serverData.getFullServerName())) ? ModColor.toRGB(240, 240, 240, 240) : -2147483648;
        int backgroundColor = hovered ? ModColor.toRGB(50, 50, 50, 120) : ModColor.toRGB(30, 30, 30, 120);

        drawRect(x - 5, (int)y - 6, x + 300, (int)y + 18, backgroundColor);
        LabyMod.getInstance().getDrawUtils().drawRectBorder(x - 5, (int)y - 6, x + 300, (int)y + 18, borderColor, 1.0D);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        LabyMod.getInstance().getDrawUtils().drawCenteredString(serverData.getServerName() + " (" + TimeFormat.get(serverData.getTotalTime(), true) + ")", this.width / 2, y +2);

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if(this.selectedString != null && this.selectedString.equals(this.hoveredString)){
            openDetailsForServer(selectedString);
            return;
        }

        if (this.hoveredString != null) {
            this.selectedString = this.hoveredString;
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);

        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        this.scrollbar.mouseInput();
    }

    private void openDetailsForServer(String serverName){
        ServerData serverData = ConnectionHistoryAddon.getInstance().getServerDataManager().getServerData(serverName);

        Minecraft.getMinecraft().displayGuiScreen(new DetailGUI(this, serverData));
    }
}
