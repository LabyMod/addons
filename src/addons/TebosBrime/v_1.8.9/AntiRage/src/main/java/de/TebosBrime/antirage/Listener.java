package de.TebosBrime.antirage;

import de.TebosBrime.antirage.gui.FilterGui;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Listener {
    static final String tag = "§7[§bAntiRage§7]§8 ";

    public Listener(){
        AntiRageAddon.getInstance().getApi().getEventManager().register(this::sendMessageCommand);
        AntiRageAddon.getInstance().getApi().registerForgeListener(this);
    }

    public boolean sendMessageCommand(String s){
        if(!s.startsWith("/+antirage")) {
            return sendMessage(s);
        }

        //AntiRage Commands
        if (s.startsWith("/+antirage add ")) {
            s = s.replace("/+antirage add ", "");
            Helper.addWordToFilter(s, true);
            AntiRageAddon.getFilterClass().save();
            return true;
        } else if (s.startsWith("/+antirage add")) {
            LabyMod.getInstance().displayMessageInChat("/+antirage §8add §8<word §8or §8phrase>");
            return true;
        }
        if (s.startsWith("/+antirage remove ")) {
            s = s.replace("/+antirage remove ", "");
            Helper.removeWordFromFilter(s, true);
            AntiRageAddon.getFilterClass().save();
            return true;
        } else if (s.startsWith("/+antirage remove")) {
            LabyMod.getInstance().displayMessageInChat("/+antirage §8remove §8<word §8or §8phrase>");
            return true;
        }


        //LIST
        if (s.startsWith("/+antirage list")) {
            LabyMod.getInstance().displayMessageInChat(" ");
            LabyMod.getInstance().displayMessageInChat(tag + "List §8contains §e" + AntiRageAddon.getFilter().size() + " §8strings.");
            for (int i = 0; i < AntiRageAddon.getFilter().size(); i++) {
                int c = i + 1;
                IChatComponent cct = new ChatComponentText(tag + c + " > §e" + AntiRageAddon.getFilter().get(i) + " ");
                IChatComponent hover = new ChatComponentText("Click to remove item");
                IChatComponent comp = new ChatComponentText("§e[REMOVE]");
                ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/+antirage remove " + AntiRageAddon.getFilter().get(i)) {
                    @Override
                    public Action getAction() {
                        return Action.RUN_COMMAND;
                    }
                }).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

                comp.setChatStyle(style);
                cct.appendSibling(comp);

                Minecraft.getMinecraft().thePlayer.addChatMessage(cct);
            }

            IChatComponent cct = new ChatComponentText(tag);
            IChatComponent hover = new ChatComponentText("Click to clear list");
            IChatComponent comp = new ChatComponentText("§e[CLEAR] ");
            ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/+antirage clear") {
                @Override
                public Action getAction() {
                    return Action.RUN_COMMAND;
                }
            }).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

            comp.setChatStyle(style);
            cct.appendSibling(comp);

            hover = new ChatComponentText("Click to reset list");
            comp = new ChatComponentText("§e[RESET] ");
            style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/+antirage reset") {
                @Override
                public Action getAction() {
                    return Action.RUN_COMMAND;
                }
            }).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

            comp.setChatStyle(style);
            cct.appendSibling(comp);


            hover = new ChatComponentText("Click to open menu");
            comp = new ChatComponentText("§e[OPEN MENU]");
            style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/+antirage openmenu") {
                @Override
                public Action getAction() {
                    return Action.RUN_COMMAND;
                }
            }).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

            comp.setChatStyle(style);
            cct.appendSibling(comp);

            Minecraft.getMinecraft().thePlayer.addChatMessage(cct);
            LabyMod.getInstance().displayMessageInChat(" ");
            return true;
        }
        if (s.startsWith("/+antirage openmenu")) {
            Minecraft.getMinecraft().displayGuiScreen(new FilterGui());
            return true;
        }
        if (s.startsWith("/+antirage save")) {
            AntiRageAddon.getFilterClass().save();
            LabyMod.getInstance().displayMessageInChat(tag + "filter §8list: §8save");
            return true;
        }
        if (s.startsWith("/+antirage load")) {
            AntiRageAddon.getFilterClass().load();
            LabyMod.getInstance().displayMessageInChat(tag + "filter §8list: §8load");
            return true;
        }
        if (s.startsWith("/+antirage download ")) {
            AntiRageAddon.getFilterClass().load(s.replace("/+antirage download ", ""));
            LabyMod.getInstance().displayMessageInChat(tag + "filter §8list: §8download");
            return true;
        }
        if (s.startsWith("/+antirage clear")) {
            AntiRageAddon.getFilterClass().clear();
            LabyMod.getInstance().displayMessageInChat(tag + "filter §8list: §8clear");
            return true;
        }
        if (s.startsWith("/+antirage reset")) {
            AntiRageAddon.getFilterClass().reset();
            LabyMod.getInstance().displayMessageInChat(tag + "filter §8list: §8reset");
            return true;
        }
        if (s.startsWith("/+antirage send ")) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(s.replace("/+antirage send ", ""));
            return true;
        }
        if (s.startsWith("/+antirage")) {
            LabyMod.getInstance().displayMessageInChat(" ");
            LabyMod.getInstance().displayMessageInChat(tag + "command list:");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8add §8<word §8or §8phrase> §7- §eadd §ea §eword §eto §efilter");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8remove §8<word §8or §8phrase> §7- §eremove §ea §eword §efrom §efilter");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8list §7- §eprint §ethe §efilter §elist");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8reset §7- §ereset §efilter §eto §edefault");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8clear §7- §eclear §efilter");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8save §7- §esave §efilter §eto §efile");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8load §7- §eload §efilter §efrom §efile");
            LabyMod.getInstance().displayMessageInChat(tag + "§8/+antirage §8download §8<link> §7- §eload §efilter §efrom §ewebpage");
            LabyMod.getInstance().displayMessageInChat(" ");
            return true;
        }
        return false;
    }

    public boolean sendMessage(String message){
        //Message Check
        String upperString = message.toUpperCase();
        if(!AntiRageAddon.getSettings().isEnabled()) {
            return false;
        }

        if(AntiRageAddon.getSettings().isExactCheck()){
            // NotImplementedYet
        }else{
            if(AntiRageAddon.getFilter().parallelStream().anyMatch(upperString::contains)){
                Optional<String> match = AntiRageAddon.getFilter().parallelStream().filter(upperString::contains).findAny();
                sendAllowMessage(match.get(), message);
                return true;
            }
        }

        return false;
    }

    private void sendAllowMessage(String matchString, String originalMessage){
        LabyMod.getInstance().displayMessageInChat(tag + "Found §e" + matchString + " §8in §8your §8message.");
        LabyMod.getInstance().displayMessageInChat(tag + "Your §8message §8was: §e" + originalMessage);

        IChatComponent cct = new ChatComponentText(tag);
        IChatComponent hover = new ChatComponentText("Click to send without filter");
        IChatComponent comp = new ChatComponentText("§e[SEND ANYWAY] ");
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/+antirage send " + originalMessage) {
            @Override
            public Action getAction() {
                return Action.RUN_COMMAND;
            }
        }).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

        comp.setChatStyle(style);
        cct.appendSibling(comp);

        hover = new ChatComponentText("Click to remove from filter list");
        comp = new ChatComponentText("§e[REMOVE] ");
        style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/+antirage remove " + matchString) {
            @Override
            public Action getAction() {
                return Action.RUN_COMMAND;
            }
        }).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

        comp.setChatStyle(style);
        cct.appendSibling(comp);

        Minecraft.getMinecraft().thePlayer.addChatMessage(cct);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        //Change filter status
        if(AntiRageAddon.getSettings() == null){
            return;
        }

        if (AntiRageAddon.getSettings().getToggleKey() != -1 && Keyboard.isKeyDown(AntiRageAddon.getSettings().getToggleKey())) {
            if(AntiRageAddon.getSettings().isEnabled()){
                LabyMod.getInstance().displayMessageInChat(tag + "Rage §8filter §8is §8temporarily §8disabled. §8(15 §8seconds)");
                AntiRageAddon.getSettings().setTODisabled(true);

                new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AntiRageAddon.getSettings().setTODisabled(false);
                    LabyMod.getInstance().displayMessageInChat(tag + "Rage §8filter §8is §8now §8activated.");
                }).start();

            }else{
                LabyMod.getInstance().displayMessageInChat(tag + "Rage §8filter §8is §8already §8disabled.");
            }
        }

        /*if (AntiRageAddon.getSettings().getMenuKey() != -1 && Keyboard.isKeyDown(AntiRageAddon.getSettings().getMenuKey())) {
            Minecraft.getMinecraft().displayGuiScreen(new FilterGui());
        }*/
    }
}