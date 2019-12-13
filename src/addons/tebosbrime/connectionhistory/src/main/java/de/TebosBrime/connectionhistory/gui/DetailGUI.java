package de.TebosBrime.connectionhistory.gui;

import de.TebosBrime.connectionhistory.util.ServerData;
import de.TebosBrime.connectionhistory.util.ServerDataConnection;
import de.TebosBrime.connectionhistory.util.TimeFormat;
import net.labymod.gui.elements.Scrollbar;
import net.labymod.main.LabyMod;
import net.labymod.utils.ModColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;

public class DetailGUI extends GuiScreen {

    private Scrollbar scrollbar = new Scrollbar(18);

    private GuiScreen lastScreen;
    private ServerData serverData;

    public DetailGUI(GuiScreen lastScreen, ServerData serverData) {

        this.lastScreen = lastScreen;
        this.serverData = serverData;

    }

    @Override
    public void initGui() {

        super.initGui();

        this.scrollbar.init();
        this.scrollbar.setPosition(this.width / 2 + 122, 44, this.width / 2 + 126, this.height - 32 - 3);

        this.buttonList.add(new GuiButton(667, this.width - (75+20), this.height - 26, 75, 20, "Back"));

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        super.actionPerformed(button);

        if(button.id == 667){
            Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(this.scrollbar.getScrollY());
        double yPos = 45.0D + this.scrollbar.getScrollY() + 3.0D;

        for (ServerDataConnection serverDataConnection : serverData.getConnections()){
            drawEntry(serverDataConnection, yPos, mouseX, mouseY);
            yPos += 18.0D;
        }

        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(0, 41);
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(this.height - 32, this.height);

        LabyMod.getInstance().getDrawUtils().drawGradientShadowTop(41.0D, 0.0D, this.width);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowBottom(this.height - 32.0D, 0.0D, this.width);

        LabyMod.getInstance().getDrawUtils().drawCenteredString("Details (" + serverData.getServerName() + ")", this.width / 2, 20);

        LabyMod.getInstance().getDrawUtils().drawString("total played time: " + TimeFormat.get(serverData.getTotalTime(), true), 12, 6, 0.66);
        LabyMod.getInstance().getDrawUtils().drawString("total connections: " + serverData.getConnections().size(),12, 6 + 10, 0.66);

        this.scrollbar.update(serverData.getConnections().size());
        this.scrollbar.draw();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawEntry(ServerDataConnection serverDataConnection, double y, int mouseX, int mouseY)
    {
        int x = this.width / 2 - 120;

        boolean hovered = (mouseX > x) && (mouseX < x + 240) && (mouseY > y -6) && (mouseY < y + 15) && (mouseX > 32) && (mouseY < this.height - 32);
        int backgroundColor = hovered ? ModColor.toRGB(50, 50, 50, 120) : ModColor.toRGB(30, 30, 30, 120);

        drawRect(x - 5, (int)y - 6, x + 240, (int)y + 15, backgroundColor);
        LabyMod.getInstance().getDrawUtils().drawRectBorder(x - 5, (int)y - 6, x + 240, (int)y + 15, -2147483648, 1.0D);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        LabyMod.getInstance().getDrawUtils().drawCenteredString(serverDataConnection.getDetailsString(), this.width / 2, y + 1);

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

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

}
