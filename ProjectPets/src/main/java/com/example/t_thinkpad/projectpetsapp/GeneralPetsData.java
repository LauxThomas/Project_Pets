package com.example.t_thinkpad.projectpetsapp;

public final class GeneralPetsData {
    private static final String[] FAMILIES = new String[]{"", "Dogs", "Cats", "Birds", "Fish", "Small Animals", "Other"};

    private static final String[] DOGS = new String[]{"", "Französische Bulldogge", "Labrador", "Australian Shepherd", "Chihuahua", "Golden Retriever", "Border Collie", "Labradoodle", "Rottweiler", "Beagle", "Mops"};
    private static final String[] CATS = new String[]{"", "Maine Coon", "Norwegische Waldkatze", "Bengalkatze", "Britisch Kurzhaar", "Siamkatze", "Ragdoll", "Savannah Katze", "Perserkatze", "Heilige Birma", "Hauskatze"};
    private static final String[] BIRDS = new String[]{"", "Amadinen", "Bergsittich", "Felsensittich", "Gelbbrustara", "Goldnackenara", "Kanarienvogel", "Königssittich", "Nymphensittich", "Weißhaubenkakadu", "Wellensittich"};
    private static final String[] FISH = new String[]{"", "Guppy", "Neonfisch", "Platy", "Kardinalfisch", "Black Molly", "Panzerwels", "Antennenwels", "Blauer Fadenfisch", "Koi", "Hammerhai"};
    private static final String[] SMALL_ANIMALS = new String[]{"", "Kaninchen", "Ratte", "Maus", "Hamster", "Chinchilla", "Hase", "Meerschweinchen", "Wüstenspringmaus"};
    private static final String[] OTHER = new String[]{"", "Känguru", "Spinne", "Schlange", "Schildkröte", "Bartagame", "Gecko", "Schwein", "Kuh", "Schaf", "Ziege"};

    public static String[] getDogs(){
        return DOGS;
    }

    public static String[] getCats(){
        return CATS;
    }

    public static String[] getBirds(){
        return BIRDS;
    }

    public static String[] getFish(){
        return FISH;
    }

    public static String[] getSmallAnimals(){
        return SMALL_ANIMALS;
    }

    public static String[] getOther(){
        return OTHER;
    }

    public static String[] getFamilies(){
        return FAMILIES;
    }
}
