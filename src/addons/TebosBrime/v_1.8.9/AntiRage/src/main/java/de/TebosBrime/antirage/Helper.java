package de.TebosBrime.antirage;

import net.labymod.main.LabyMod;

public class Helper {

    public static void addWordToFilter(String word, boolean withMessage){
        if(!AntiRageAddon.getFilter().contains(word.toUpperCase())){
            AntiRageAddon.getFilter().add(word.toUpperCase());
        }

        if(withMessage){
            LabyMod.getInstance().displayMessageInChat(Listener.tag + "Added §e" + word + " §8to §8filter §8list");
        }
    }

    public static void removeWordFromFilter(String word, boolean withMessage){
        if(AntiRageAddon.getFilter().contains(word.toUpperCase())){
            AntiRageAddon.getFilter().remove(word.toUpperCase());
            if(withMessage){
                LabyMod.getInstance().displayMessageInChat(Listener.tag + "Removed §e" + word + " §8from §8filter §8list");
            }
        } else {
            if(withMessage){
                LabyMod.getInstance().displayMessageInChat(Listener.tag + "§e" + word + " §8is §8not §8in §8the §8filter §8list");
            }
        }
    }
}
