package de.TebosBrime.translate.enums;

public enum EnumModuleTranslatorSources {
    DEFAULT(false),
    GOOGLE_TRANSLATE(false),
    //THESAURUS(true),
    ;

    private boolean needKey;
    EnumModuleTranslatorSources(boolean needKey){
        this.needKey = needKey;
    }

    public boolean needKey(){
        return needKey;
    }

    public static EnumModuleTranslatorSources fromString(String x) {
        for(EnumModuleTranslatorSources enumModuleTranslatorSources : values()){
            if(enumModuleTranslatorSources.name().equalsIgnoreCase(x)){
                return enumModuleTranslatorSources;
            }
        }
        return DEFAULT;
    }
 }