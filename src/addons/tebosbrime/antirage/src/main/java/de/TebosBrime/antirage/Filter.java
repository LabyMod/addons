package de.TebosBrime.antirage;

import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Filter {

    private List<String> filter = new ArrayList<>();
    private File file = new File( Minecraft.getMinecraft().mcDataDir + "\\LabyMod\\ANTIRAGE_filterlist.json");

    public Filter(){
        this.load();
    }

    public List<String> getFilter(){
        return filter;
    }

    public void reset(){
        defaultValues();
    }

    public void clear(){
        filter.clear();
        save();
    }

    public void load(){
        System.out.println("Load file: " + file.toString());
        if(!file.exists()){
            defaultValues();
        }else{
            try {
                filter.clear();
                filter = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
            } catch (IOException e) {
                defaultValues();
                e.printStackTrace();
            }
        }
        save();
    }

    public void load(String url) {
        try {
            InputStream in = new URL(url).openStream();
            Files.copy(in, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);

            load();
        }catch (MalformedURLException e) {
            LabyMod.getInstance().displayMessageInChat(Listener.tag + "filter §8list: §8can't §8load §8" + url);
            LabyMod.getInstance().displayMessageInChat(Listener.tag + "§8see §8log §8for §8help!");
            e.printStackTrace();
        } catch (IOException e) {
            LabyMod.getInstance().displayMessageInChat(Listener.tag + "filter §8list: §8can't §8load §8" + url);
            LabyMod.getInstance().displayMessageInChat(Listener.tag + "§8see §8log §8for §8help!");
            e.printStackTrace();
        }
    }

    public void save() {
        System.out.println("Save file: " + file.toString());

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(file.toPath(), String.join("\n", filter).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void defaultValues(){
        filter.clear();
        filter.add("EXAMPLE ENTRY");

        save();
    }
}
