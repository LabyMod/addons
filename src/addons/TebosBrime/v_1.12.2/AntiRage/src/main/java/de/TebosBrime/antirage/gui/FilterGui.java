package de.TebosBrime.antirage.gui;

import de.TebosBrime.antirage.AntiRageAddon;
import de.TebosBrime.antirage.Helper;
import net.labymod.gui.elements.Scrollbar;
import net.labymod.gui.elements.Tabs;
import net.labymod.main.LabyMod;
import net.labymod.utils.ModColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.io.IOException;

public class FilterGui extends GuiScreen {

    private Scrollbar scrollbar = new Scrollbar(15);
    private String selectedString = null;
    private String hoveredString = null;
    private GuiButton buttonRemove;

    public void initGui() {
        super.initGui();

        this.scrollbar.init();
        this.scrollbar.setPosition(this.width / 2 + 102, 44, this.width / 2 + 106, this.height - 32 - 3);
        this.scrollbar.setSpeed(10);

        this.buttonList.add(this.buttonRemove = new GuiButton(1, this.width / 2 - 100, this.height - 26, 90, 20, "Remove"));
        this.buttonList.add(new GuiButton(4, 10, this.height - 26, 120, 20, "Download from web"));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 10, this.height - 26, 90, 20, "Add"));
        //this.buttonList.add(new GuiButton(3, this.width - 130, this.height - 26, 120, 20, "Close"));

        Tabs.initGuiScreen(this.buttonList, this);
    }

    public void onGuiClosed() {
        super.onGuiClosed();

        AntiRageAddon.getFilterClass().save();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        switch (button.id){
            case 1:
                //Remove
                this.buttonRemove.enabled = false;
                Helper.removeWordFromFilter(selectedString, false);
                break;
            case 2:
                //Add
                Minecraft.getMinecraft().displayGuiScreen(new AddGui(this));
                break;
            /*case 3:
                //Close
                Minecraft.getMinecraft().displayGuiScreen(null);
                break;*/
            case 4:
                //download list
                Minecraft.getMinecraft().displayGuiScreen(new DownloadGui(this));
                break;
        }
        Tabs.actionPerformedButton(button);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        LabyMod.getInstance().getDrawUtils().drawAutoDimmedBackground(this.scrollbar.getScrollY());
        this.hoveredString = null;
        double yPos = 45.0D + this.scrollbar.getScrollY() + 3.0D;

        for (String item : AntiRageAddon.getFilter()){
            drawEntry(item, yPos, mouseX, mouseY);
            yPos += 15.0D;
        }
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(0, 41);
        LabyMod.getInstance().getDrawUtils().drawOverlayBackground(this.height - 32, this.height);

        LabyMod.getInstance().getDrawUtils().drawGradientShadowTop(41.0D, 0.0D, this.width);
        LabyMod.getInstance().getDrawUtils().drawGradientShadowBottom(this.height - 32, 0.0D, this.width);

        LabyMod.getInstance().getDrawUtils().drawCenteredString("Your filterlist", this.width / 2, 29.0D);

        this.scrollbar.update(AntiRageAddon.getFilter().size());
        this.scrollbar.draw();

        this.buttonRemove.enabled = (this.selectedString != null);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawEntry(String key, double y, int mouseX, int mouseY) {
        int x = this.width / 2 - 100;

        boolean hovered = (mouseX > x) && (mouseX < x + 200) && (mouseY > y - 4) && (mouseY < y + 11) && (mouseX > 32) && (mouseY < this.height - 32);
        if (hovered) {
            this.hoveredString = key;
        }
        int borderColor = this.selectedString == key ? ModColor.toRGB(240, 240, 240, 240) : -2147483648;
        int backgroundColor = hovered ? ModColor.toRGB(50, 50, 50, 120) : ModColor.toRGB(30, 30, 30, 120);

        drawRect(x - 5, (int)y - 4, x + 200, (int)y + 11, backgroundColor);
        LabyMod.getInstance().getDrawUtils().drawRectBorder(x - 5, (int)y - 4, x + 200, (int)y + 11, borderColor, 1.0D);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        LabyMod.getInstance().getDrawUtils().drawString(key, x + 11, y + 1.0D);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.hoveredString != null) {
            this.selectedString = this.hoveredString;
        }
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.CLICKED);
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.DRAGGING);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        this.scrollbar.mouseAction(mouseX, mouseY, Scrollbar.EnumMouseAction.RELEASED);
        super.mouseReleased(mouseX, mouseY, state);
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        this.scrollbar.mouseInput();
    }
}