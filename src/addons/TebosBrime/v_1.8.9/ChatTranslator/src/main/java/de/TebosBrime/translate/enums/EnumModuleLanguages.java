package de.TebosBrime.translate.enums;

public enum EnumModuleLanguages {
    SYSTEM_DEFAULT,
    ENGLISH,
    GERMAN,
    FRENCH,
    SPANISH,
    RUSSIAN,
    PORTUGUESE,
    ITALIAN,
    ;

    public static int fromStringToInt(String x){
        for(int i = 1; i<= 7; i++){
            if(fromIntToShortString(i).equals(x))
                return i;
        }
        return 0;
    }

    public static EnumModuleLanguages fromInt(int x) {
        switch(x) {
            case 0:
                return SYSTEM_DEFAULT;
            case 1:
                return ENGLISH;
            case 2:
                return GERMAN;
            case 3:
                return FRENCH;
            case 4:
                return SPANISH;
            case 5:
                return RUSSIAN;
            case 6:
                return PORTUGUESE;
            case 7:
                return ITALIAN;
        }
        return SYSTEM_DEFAULT;
    }


    public static String fromIntToLongString(int x) {
        switch(x) {
            case 0:
                return "system_default";
            case 1:
                return "en_US";
            case 2:
                return "de_DE";
            case 3:
                return "fr_FR";
            case 4:
                return "es_ES";
            case 5:
                return "ru_RU";
            case 6:
                return "pt_RT";
            case 7:
                return "it_IT";
        }
        return "system_default";
    }

    public static String fromIntToShortString(int x) {
        switch(x) {
            case 0:
                return "system_default";
            case 1:
                return "en";
            case 2:
                return "de";
            case 3:
                return "fr";
            case 4:
                return "es";
            case 5:
                return "ru";
            case 6:
                return "pt";
            case 7:
                return "it";
        }
        return "system_default";
    }

    public static int fromEnum(EnumModuleLanguages x) {
        switch(x) {
            case SYSTEM_DEFAULT:
                return 0;
            case ENGLISH:
                return 1;
            case GERMAN:
                return 2;
            case FRENCH:
                return 3;
            case SPANISH:
                return 4;
            case RUSSIAN:
                return 5;
            case PORTUGUESE:
                return 6;
            case ITALIAN:
                return 7;
        }
        return 0;
    }

}
