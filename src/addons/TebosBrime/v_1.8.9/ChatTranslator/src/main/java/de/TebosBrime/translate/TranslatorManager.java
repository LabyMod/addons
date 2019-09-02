package de.TebosBrime.translate;

import de.TebosBrime.translate.api.DefaultTranslatorAPI;
import de.TebosBrime.translate.api.GoogleTranslateAPI;
import de.TebosBrime.translate.api.ThesaurusTranslateAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranslatorManager {

    public static DefaultTranslatorAPI getTrangslator(int translatorID){
        //AUTO
        if(translatorID == 0){
            return new GoogleTranslateAPI();
        }
        if(translatorID == 1){
            return new GoogleTranslateAPI();
        }
        if(translatorID == 2){
            return new ThesaurusTranslateAPI();
        }

        return new GoogleTranslateAPI();
    }
}
