package de.TebosBrime.translate;

import de.TebosBrime.translate.utils.MD5;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.HashMap;
import java.util.UUID;

public class Listener {

    private String lastMessage = UUID.randomUUID().toString();
    private HashMap<String, String> messages = new HashMap<>();
    public HashMap<String, String> translations = new HashMap<>();

    public Listener(){
        TranslatorAddon.getInstance().getApi().getEventManager().register(this::sendMessage);
        TranslatorAddon.getInstance().getApi().getEventManager().register(this::modifyChatMessage);
    }

    private String translationText(String s){
        return "§7Translation: §f" + s;
    }

    private boolean sendMessage(String s){
        if(s.startsWith("/+translate ")){
            String message = s.replace("/+translate ", "");

            String MD5String = MD5.getMD5(message);
            messages.put(MD5String, message);

            s = "/+translateNow " + MD5String;
        }

        if(s.startsWith("/+translateNow ")){
            String hash = s.replace("/+translateNow ", "");

            if(!messages.containsKey(hash)){
                lastMessage = "[Translate] An error has occurred - 001";
                LabyMod.getInstance().displayMessageInChat("[Translate] An error has occurred - 001");
                return true;
            }

            final Thread translationThread = new Thread(() -> {
                String translation;
                if(translations.containsKey(hash)){
                    translation = translationText(translations.get(hash));
                }else {
                    String text = messages.get(hash).replace("\n", " ");
                    translation = TranslatorAddon.getTranslator().translate(text);
                    translations.put(hash, translation);
                    translation = translationText(translation);
                }

                lastMessage = translation;
                LabyMod.getInstance().displayMessageInChat(translation);
            });

            final Thread inspectThread = new Thread(() -> {
                int sleepSeconds = 10;
                try {
                    Thread.sleep(sleepSeconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(translationThread.isAlive()) {
                    translationThread.suspend();

                    String a = "[Translate] API did not reply within "+sleepSeconds+" seconds..";
                    lastMessage = a;
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(a));
                }
            });

            translationThread.start();
            inspectThread.start();
            return true;
        }
        return false;
    }

    private Object modifyChatMessage(Object o){
        if(!TranslatorAddon.getSettings().isEnabled())
            return o;

        try {
            ITextComponent cct = (ITextComponent) o;
            String original = cct.getUnformattedText();

            if(original.equals(lastMessage))
                return o;
            lastMessage = cct.getUnformattedText();
            if(original.length() == 0)
                return o;
            String withOut = original.replace("\t", "").replace("\n", "").replace("\r", "").replace(" ", "");
            if(withOut.length() == 0)
                return o;

            for(ITextComponent cc : cct.getSiblings()){
                if(cc.getStyle() != null && cc.getStyle().getHoverEvent() != null &&
                        cc.getStyle().getHoverEvent().getValue() != null && cc.getStyle().getHoverEvent().getValue().getUnformattedText() != null &&
                        cc.getStyle().getHoverEvent().getValue().getUnformattedText().startsWith(translationText(""))){
                    return o;
                }
            }

            String MD5String = MD5.getMD5(original);
            messages.put(MD5String, original);

            if(!TranslatorAddon.getSettings().isTranslateAtHover()) {
                ITextComponent comp = new TextComponentString(TranslatorAddon.getSettings().getTranslation_icon().replace("&", "§"));
                comp = addHoverAndCommand(comp, "Click here to translate: (" + TranslatorAddon.getTranslator().getLangCode() + ")", "/+translateNow " + MD5String);
                cct.appendSibling(comp);

                lastMessage = cct.getUnformattedText();
                return cct;
            }else{
                startThreads(MD5String, cct, original);
                return dontShow();
            }
        }catch (Exception e){
            e.printStackTrace();
            return o;
        }
    }

    private ITextComponent dontShow(){
        lastMessage = " ";

        return new TextComponentString(" ");
    }

    private void startThreads(final String MD5String, final ITextComponent cct, final String original){
        new Thread(() -> {
            HashMap<String, Long> ids = new HashMap<>();

            Thread translationThread = new Thread(() -> {
                String translation = TranslatorAddon.getTranslator().translate(original);
                translations.put(MD5String, translation);

                ITextComponent cc = cct;
                ITextComponent comp = new TextComponentString(TranslatorAddon.getSettings().getTranslation_icon().replace("&", "§"));
                comp = addHoverAndCommand(comp, translationText(translations.get(MD5String)), "/+translateNow " + MD5String);
                cc.appendSibling(comp);

                lastMessage = cc.getUnformattedText();
                Minecraft.getMinecraft().player.sendMessage(cc);

                Thread tr = null;
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    if (t.getId() == ids.get(MD5String)) {
                        tr = t;
                        break;
                    }
                }
                if(tr != null){
                    if(tr.isAlive()){
                        System.out.println("killing timer thread");
                        tr.suspend();
                    }else{
                        System.out.println("timer thread is not running");
                    }
                }
            });

            Thread inspectorThread = new Thread(() -> {
                try {
                    Thread.sleep(TranslatorAddon.getSettings().getDelaySeconds() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(translationThread.isAlive()) {
                    System.out.println("killing translation thread");
                    translationThread.suspend();
                    ITextComponent cc = cct;

                    ITextComponent comp = new TextComponentString(TranslatorAddon.getSettings().getTranslation_icon().replace("&", "§"));
                    comp = addHoverAndCommand(comp,
                            "API did not reply within " + TranslatorAddon.getSettings().getDelaySeconds() +
                                    " seconds.. Click here to translate manually: (" + TranslatorAddon.getTranslator().getLangCode() + ")",
                            "/+translate " + MD5String);
                    cc.appendSibling(comp);


                    lastMessage = cc.getUnformattedText();
                    Minecraft.getMinecraft().player.sendMessage(cc);
                }else{
                    System.out.println("translator thread is not running");
                }
            });

            translationThread.start();
            inspectorThread.start();
            long id = inspectorThread.getId();
            ids.put(MD5String, id);

        }).start();
    }

    private ITextComponent addHoverAndCommand(ITextComponent comp, String hover, String command){
        Style style = new Style().setClickEvent(
            new ClickEvent(ClickEvent.Action.RUN_COMMAND, command) {
                @Override
                public Action getAction() {
                    return Action.RUN_COMMAND;
                }
            })
            .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(hover)));
        comp.setStyle(style);
        return comp;
    }
}
