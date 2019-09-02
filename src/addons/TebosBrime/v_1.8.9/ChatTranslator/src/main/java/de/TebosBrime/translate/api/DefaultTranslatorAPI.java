package de.TebosBrime.translate.api;

import de.TebosBrime.translate.enums.EnumModuleLanguages;
import de.TebosBrime.translate.utils.MD5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public abstract class DefaultTranslatorAPI {

    protected String langCode;
    protected String apiKey;

    public void setOutputLanguage(int languageId){
        if(languageId == 0){
            langCode = System.getProperty("user.language");
            System.out.println("System Language: " + langCode);
            return;
        }

        langCode = EnumModuleLanguages.fromIntToShortString(languageId);
        System.out.println("Changed language to: " + langCode);
    }

    public void setAPIKey(String apiKey) {
        this.apiKey = apiKey;
        System.out.println("Changed apiKey to: " + MD5.getMD5(apiKey));
    }

    public abstract String translate(String message);

    public String getLangCode(){
        return langCode;
    }


    public Response readURL(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("content-type", "application/json");

        int responseCode = connection.getResponseCode();
        if(responseCode != 200)
            return new Response(null, responseCode);

        StringBuffer response = new StringBuffer();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String s = response.toString();
        s = s.replace("\\u003c", "<")
                .replace("\\u003e", ">")
                .replace("\\\"", "");

        s = s.trim();

        return new Response(s, responseCode);
    }

    private static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-OR]");

    public String stripColors(String input) {
        if (input == null) {
            return null;
        }

        String result = STRIP_COLOR_PATTERN.matcher(input.replace("\n", " "))
                .replaceAll("");
        return result;
    }

    public class Response {

        private String response;
        private int code;
        public Response(String r, int c){
            this.response = r;
            this.code = c;
        }

        public String getResponse(){
            return response;
        }

        public int getCode(){
            return code;
        }
    }
}
