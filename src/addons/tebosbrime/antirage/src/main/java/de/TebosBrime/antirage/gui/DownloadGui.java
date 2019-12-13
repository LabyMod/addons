package de.TebosBrime.antirage.gui;

import de.TebosBrime.antirage.AntiRageAddon;
import net.labymod.gui.elements.ModTextField;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class DownloadGui extends GuiScreen {

    private GuiScreen lastScreen;
    private ModTextField link;
    private GuiButton buttonDone;
    private String storedLink;

    public DownloadGui(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
    }

    public void initGui() {
        super.initGui();

        Keyboard.enableRepeatEvents(true);

        this.link = new ModTextField(-1, LabyMod.getInstance().getDrawUtils().fontRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20);
        this.link.setMaxStringLength(256);

        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 + 3, this.height / 2 + 35, 98, 20, "Download & use"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 101, this.height / 2 + 35, 98, 20, "Cancel"));

        this.buttonDone.enabled = false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.link.textboxKeyTyped(typedChar, keyCode)) {
            this.storedLink = this.link.getText();
        }

        try{
            this.buttonDone.enabled = !this.storedLink.isEmpty();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        super.keyTyped(typedChar, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean flag = this.link.isFocused();
        this.link.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        switch (button.id){
            case 0:
                AntiRageAddon.getFilterClass().load(this.storedLink);
                Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
                break;
        }
    }

    public void updateScreen() {
        super.updateScreen();

        this.link.updateCursorCounter();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.link.drawTextBox();
        LabyMod.getInstance().getDrawUtils().drawString("Download link:", this.width / 2 - 100, this.height / 2 - 65);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
