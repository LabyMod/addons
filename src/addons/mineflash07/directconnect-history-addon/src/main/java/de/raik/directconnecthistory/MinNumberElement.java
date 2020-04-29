package de.raik.directconnecthistory;

import net.labymod.settings.elements.NumberElement;


/*
 * Sub class of Numberlement to set a minimum
 */

public class MinNumberElement extends NumberElement {

    /*
     * Using other constructor to set all needed values
     */
    public MinNumberElement(String displayName, String configEntryName, IconData iconData, int min, int currentValue) {
        //Use the best available super constructor
        super(displayName, iconData, currentValue);

        //Set other needed attributes
        this.setMinValue(min);
        this.setConfigEntryName(configEntryName);
    }
}
