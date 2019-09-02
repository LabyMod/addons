package de.TebosBrime.translate.enums;

public enum EnumModuleTranslatorSources {
    DEFAULT(false),
    GOOGLE_TRANSLATE(false),
    //THESAURUS(true),
    ;

    boolean needKey;
    EnumModuleTranslatorSources(boolean needKey){
        this.needKey = needKey;
    }

    public static boolean needKey(int id){
        EnumModuleTranslatorSources enumModuleTranslatorSources = fromInt(id);

        return enumModuleTranslatorSources.needKey;
    }

    public static EnumModuleTranslatorSources fromInt(int x) {
        switch(x) {
            case 0:
                return DEFAULT;
            case 1:
                return GOOGLE_TRANSLATE;
            /*case 2:
                return THESAURUS;*/
        }
        return DEFAULT;
    }

    public static int fromEnum(EnumModuleTranslatorSources x) {
        switch(x) {
            case DEFAULT:
                return 0;
            case GOOGLE_TRANSLATE:
                return 1;
           /* case THESAURUS:
                return 2;*/
        }
        return 0;
    }

 }
