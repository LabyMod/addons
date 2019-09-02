package de.TebosBrime.translate.api;

import com.google.gson.*;

import java.net.URLEncoder;

public class GoogleTranslateAPI extends DefaultTranslatorAPI {

    @Override
    public String translate(String text){
        text = stripColors(text);
        text = "\"" + text + "\"";

        try {
            String translation = callUrlAndParseResult(langCode, text);
            System.out.println("Translation for " + text + " is: " + translation + " (to " + langCode + ")");
            return translation;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error has occurred - 002";
        }
    }

    @Override
    public String getLangCode() {
        return langCode;
    }

    private String callUrlAndParseResult(String langTo, String text) throws Exception{
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + "auto" +
                "&tl=" + langTo +
                "&dt=t" +
                "&ie=UTF-8" +
                "&oe=UTF-8" +
                "&q=" + URLEncoder.encode(text, "UTF-8");

        String s = readURL(url).getResponse();

        Gson gson = new GsonBuilder().create();
        JsonArray jsonArray = gson.fromJson(s, JsonArray.class);
        jsonArray = jsonArray.get(0).getAsJsonArray();
        jsonArray = jsonArray.get(0).getAsJsonArray();
        JsonElement jsonElement = jsonArray.get(0);

        String translation = jsonElement.getAsString().trim();


        if(translation.startsWith(String.valueOf(startChar)) && translation.endsWith(String.valueOf(endChar))){
            translation = translation.substring(1, translation.length() -1);
        }
        if(translation.startsWith("«") && translation.endsWith("»")){
            translation = translation.substring(1, translation.length() -1);
        }

        /*
        System.out.println("Request: " + url);
        System.out.println("Original Text: " + text);
        System.out.println("Translation langFrom: " + langFrom);
        System.out.println("Translation langTo: " + langTo);
        System.out.println("Translation json: " + s);
        System.out.println("Translation: " + translation);
         */

        return translation;
    }

    public static final char startChar = '\u0171';
    public static final char endChar = '\u0187';

}
