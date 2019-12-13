package de.TebosBrime.antirage.gui;

import de.TebosBrime.antirage.AntiRageAddon;
import de.TebosBrime.antirage.Helper;
import net.labymod.gui.elements.ModTextField;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class AddGui extends GuiScreen {

    private GuiScreen lastScreen;
    private ModTextField fieldWord;
    private GuiButton buttonDone;
    private String storedWord;

    public AddGui(GuiScreen lastScreen) {
        this.lastScreen = lastScreen;
    }

    public void initGui() {
        super.initGui();

        Keyboard.enableRepeatEvents(true);

        this.fieldWord = new ModTextField(-1, LabyMod.getInstance().getDrawUtils().fontRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20);
        this.fieldWord.setMaxStringLength(128);

        this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 + 3, this.height / 2 + 35, 98, 20, "Add"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 101, this.height / 2 + 35, 98, 20, "Cancel"));

        this.buttonDone.enabled = false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.fieldWord.textboxKeyTyped(typedChar, keyCode)) {
            this.storedWord = this.fieldWord.getText();
        }

        try{this.buttonDone.enabled = !this.storedWord.isEmpty();}catch (NullPointerException e){}
        super.keyTyped(typedChar, keyCode);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        boolean flag = this.fieldWord.isFocused();
        this.fieldWord.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        switch (button.id) {
            case 0:
                Helper.addWordToFilter(this.storedWord, false);
                AntiRageAddon.getFilterClass().save();
                Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
                break;
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(this.lastScreen);
                break;
        }
    }

    public void updateScreen() {
        super.updateScreen();

        this.fieldWord.updateCursorCounter();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.fieldWord.drawTextBox();
        LabyMod.getInstance().getDrawUtils().drawString("Word or Phrase:", this.width / 2 - 100, this.height / 2 - 65);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
