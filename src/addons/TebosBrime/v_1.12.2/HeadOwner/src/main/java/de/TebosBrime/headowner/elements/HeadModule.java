package de.TebosBrime.headowner.elements;

import de.TebosBrime.headowner.utils.utils;
import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.ingamegui.moduletypes.SimpleModule;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public class HeadModule extends SimpleModule {

    public HeadModule(){
    }

    @Override
    public String getDisplayName() {
        return "HeadOwner";
    }

    @Override
    public String getDisplayValue() {
        utils.Skull skull = utils.getSkullLooking();
        return skull.getDisplay();
    }

    @Override
    public String getDefaultValue() {
        return "?";
    }

    @Override
    public ControlElement.IconData getIconData() {
        return new ControlElement.IconData(Material.SKULL_ITEM);
    }

    @Override
    public void loadSettings() {
    }

    @Override
    public String getSettingName() {
        return "HeadOwner";
    }

    @Override
    public String getDescription() {
        return "Shows the owner of the head you're looking at";
    }

    @Override
    public int getSortingId() {
        return 200;
    }

    @Override
    public ModuleCategory getCategory(){
        return ModuleCategoryRegistry.CATEGORY_INFO;
    }

    @Override
    public String getControlName() {
        return "HeadOwner";
    }

    @Override
    public boolean isShown(){
        utils.Skull skull = utils.getSkullLooking();

        return skull.isShown();
    }
}
