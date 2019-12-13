package de.TebosBrime.translate.api;

import com.google.gson.*;
import de.TebosBrime.translate.enums.EnumModuleLanguages;

import java.net.URLEncoder;

public class ThesaurusTranslateAPI extends DefaultTranslatorAPI {

    @Override
    public void setOutputLanguage(int languageId){
        if(languageId == 0){
            langCode = System.getProperty("user.language");
            langCode = EnumModuleLanguages.fromIntToLongString(EnumModuleLanguages.fromStringToInt(langCode));

            System.out.println("System Language: " + langCode);
            return;
        }

        langCode = EnumModuleLanguages.fromIntToLongString(languageId);
        System.out.println("Changed language to: " + langCode);
    }

    @Override
    public String translate(String text){
        text = stripColors(text);
        try {
            String translation = callUrlAndParseResult(langCode, text);
            System.out.println("Translation for " + text + " is: " + translation + " (to " + langCode + ")");
            return translation;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error has occurred - 002";
        }
    }

    private String callUrlAndParseResult(String langTo, String text) throws Exception{
        String url = "http://thesaurus.altervista.org/thesaurus/v1?" +
                "&language=" + langTo +
                "&output=json" +
                "&key=" + apiKey +
                "&word=" + URLEncoder.encode(text, "UTF-8");

        Response response = readURL(url);

        //System.out.println("Request: " + url);
        //System.out.println(response.getCode() + " code");

        if(response.getCode() == 403){
            return "wrong api key / over rate limit";
        }
        if(response.getCode() == 404){
            return "Thesaurus API can only translate single words, may use /+translate <your word>";
        }

        String s = response.getResponse();

        Gson gson = new GsonBuilder().create();
        JsonObject jsonObject = gson.fromJson(s, JsonObject.class);
        JsonArray jsonArray = jsonObject.get("response").getAsJsonArray();
        JsonObject jsonObject2 = jsonArray.get(0).getAsJsonObject().get("list").getAsJsonObject();
        String translation = jsonObject2.get("synonyms").getAsString();

        translation = translation.split("\\|")[0];

        //System.out.println("Original Text: " + text);
        //System.out.println("Translation langTo: " + langTo);
        //System.out.println("Translation json: " + s);
        //System.out.println("Translation: " + translation);

        return translation;
    }



}
