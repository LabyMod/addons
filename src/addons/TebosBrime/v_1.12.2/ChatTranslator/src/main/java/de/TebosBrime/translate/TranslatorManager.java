package de.TebosBrime.translate;

import de.TebosBrime.translate.api.DefaultTranslatorAPI;
import de.TebosBrime.translate.api.GoogleTranslateAPI;
import de.TebosBrime.translate.api.ThesaurusTranslateAPI;
import de.TebosBrime.translate.enums.EnumModuleTranslatorSources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranslatorManager {

    public static DefaultTranslatorAPI getTrangslator(EnumModuleTranslatorSources translatorSources){
        //AUTO
        if(translatorSources == EnumModuleTranslatorSources.GOOGLE_TRANSLATE){
            return new GoogleTranslateAPI();
        }
        if(translatorSources == EnumModuleTranslatorSources.GOOGLE_TRANSLATE){
            return new GoogleTranslateAPI();
        }
        /*if(translatorSources == EnumModuleTranslatorSources.THESAURUS){
            return new ThesaurusTranslateAPI();
        }*/

        return new GoogleTranslateAPI();
    }
}
