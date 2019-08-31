package de.TebosBrime.chattime;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Listener {

    private String lastMessage = UUID.randomUUID().toString();

    public Listener(){
        ChatTimeAddon.getInstance().getApi().getEventManager().register(this::modifyChatMessage);
    }

    private Object modifyChatMessage(Object o){
        if(!ChatTimeAddon.getSettings().isEnabled())
            return o;

        try {
            ITextComponent cct = (ITextComponent) o;
            if(cct.getUnformattedText().equals(lastMessage) || cct.getUnformattedText().trim().isEmpty())
                return o;

            String prefix = "§f" + timeFormat() + "§f";

            if(ChatTimeAddon.getSettings().isBefore()) {
                ITextComponent base = new TextComponentString(prefix);
                if(ChatTimeAddon.getSettings().showHoverTime()){
                    base.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(timeFormatHover()))));
                }
                base.appendSibling(cct);

                lastMessage = base.getUnformattedText();
                return base.createCopy();
            }else{
                ITextComponent base = new TextComponentString(prefix);
                if(ChatTimeAddon.getSettings().showHoverTime()){
                    base.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(timeFormatHover()))));
                }
                cct.appendSibling(base);

                lastMessage = cct.getUnformattedText();
                return cct;
            }
        }catch (Exception e){
            e.printStackTrace();
            return o;
        }
    }

    private String timeFormat(){
        String format = ChatTimeAddon.getSettings().getChatData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String time = LocalDateTime.now().format(formatter);

        return ChatTimeAddon.getSettings().getChatData2().replace("%time%", time).replace("&", "§");
    }

    private String timeFormatHover(){
        String format = ChatTimeAddon.getSettings().getChatData();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String time = LocalDateTime.now().format(formatter);

        return ChatTimeAddon.getSettings().getHoverTime().replace("%time%", time).replace("&", "§");
    }
}
