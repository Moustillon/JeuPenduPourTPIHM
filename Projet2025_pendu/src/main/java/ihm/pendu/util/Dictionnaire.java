package ihm.pendu.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import ihm.pendu.model.CategorieMot;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Dictionnaire {
    private static final Random RANDOM = new Random();
    private static final Map<CategorieMot, List<String>> MOTS_PAR_CATEGORIE = new EnumMap<>(CategorieMot.class);

    static {
        MOTS_PAR_CATEGORIE.put(CategorieMot.JAVA_FX, Arrays.asList(
            // 4 lettres
            "NODE", "MENU", "PANE", "VIEW",
            // 5 lettres
            "SCENE", "STAGE", "LABEL", "PANEL",
            // 6 lettres
            "BUTTON", "SLIDER", "DIALOG",
            // 7 lettres
            "TEXTBOX", "TOOLBAR", "TOOLTIP",
            // 8 lettres
            "COMBOBOX", "LISTVIEW", "GRIDPANE",
            // 9 lettres
            "SCROLLBAR", "SEPARATOR", "TABLELENS", "TABLEVIEW",
            // 10 lettres
            "SCROLLPANE", "TITLEDPANE", "MENUBUTTON",
            // 11 lettres
            "PROGRESSBAR", "RADIOBUTTON", "TOGGLEGROUP", "APPLICATION",
            // 12 lettres
            "STACKEDCHART", "BARCHART3D", "PIECHART3D"
        ));
        MOTS_PAR_CATEGORIE.put(CategorieMot.ANIMAUX, Arrays.asList(
            // 4 lettres
            "CHAT", "LION", "OURS",
            // 5 lettres
            "CHIEN", "TIGRE", "ZEBRE",
            // 6 lettres
            "GIRAFE", "PANDA", "JAGUAR",
            // 7 lettres
            "GORILLE", "GAZELLE",  "MANCHOT",
            // 8 lettres
            "PAPILLON", "ANTILOPE", "PANTHERE",
            // 9 lettres
            "CROCODILE", "KANGOUROU", "PERROQUET", "CHIMPANZE",
            // 10 lettres
            "RHINOCEROS", "ORANGOUTAN", "CHIMPANZEE", "SALAMANDRE",
            // 11 lettres
            "HIPPOPOTAME", "DROMADAIRES", "CROCODILIEN",
            // 12 lettres
            "HIPPOPOTAMES"
        ));
        MOTS_PAR_CATEGORIE.put(CategorieMot.FRUITS, Arrays.asList(
            // 4 lettres
            "KIWI", "POIRE", "FIGUE",
            // 5 lettres
            "PRUNE", "PECHE", "MELON",
            // 6 lettres
            "ORANGE", "MANGUE", "CERISE", "CITRON",
            // 7 lettres
            "BANANES", "FRAISES", "PASSION",
            // 8 lettres
            "PASTEQUE", "ABRICOTS", "MYRTILLE",
            // 9 lettres
             "FRAMBOISE","PAMPLEMOUSSE", "GROSEILLE", "MANDARINE", "MYRTILLES", "NECTARINE",
            // 10 lettres
            "GRENADINES", "FRAMBOISES", "CLEMENTINE", "MANDARINES",
            // 11 lettres
            "PAMPLEMOUSE", "CLEMENTINES",
            // 12 lettres
            "PAMPLEMOUSES"
        ));
        MOTS_PAR_CATEGORIE.put(CategorieMot.COULEURS, Arrays.asList(
            // 4 lettres
            "BLEU", "NOIR", "GRIS",
            // 5 lettres
            "ROUGE", "BLANC", "JAUNE",
            // 6 lettres
            "VIOLET", "ORANGE", "MARRON",
            // 7 lettres
            "MAGENTA", "INDIGOS", "POURPRE",
            // 8 lettres
            "BORDEAUX", "ECARLATE", "VERMILLON", "EMERAUDE",
            // 9 lettres
            "TURQUOISE", "AMETHYSTE", "VERMILLON", "CARAMINE",
            // 10 lettres
            "CHARTREUSE", "TURQUOISES",
            // 11 lettres
            "CHARTREUSES", "ULTRAMARINE", "BORDEAUXVIN",
            // 12 lettres
            "ROUGEMAGENTA", "VERTEMERAUDE" // ;-)
        ));
        MOTS_PAR_CATEGORIE.put(CategorieMot.PAYS, Arrays.asList(
            // 4 lettres
            "MALI", "CUBA", "PERU",
            // 5 lettres
            "MAROC","CHINE", "JAPON", "KENYA",
            // 6 lettres
            "FRANCE",  "BRESIL", "CANADA", "ITALIE", "SUISSE",
            // 7 lettres
             "ESPAGNE", "POLOGNE",
            // 8 lettres
            "BELGIQUE", "PORTUGAL", "TANZANIE", "CAMBODGE",
            // 9 lettres
            "ALLEMAGNE", "INDONESIE", "AUSTRALIE", "ARGENTINE",
            // 10 lettres
            "KAZAKHSTAN", "MADAGASCAR",  "BANGLADESH", "MAURITANIE",
            // 11 lettres
            "TADJIKISTAN", "PHILIPPINES", "AZERBAIDJAN",  "OUZBEKISTAN",
            // 12 lettres
            "TURKMENISTAN", "KIRGHIZISTAN"
        ));
        MOTS_PAR_CATEGORIE.put(CategorieMot.METIERS, Arrays.asList(
            // 4 lettres
            "CHEF", "JUGE", "SAGE",
            // 5 lettres
            "AGENT", "GARDE", "MAIRE",
            // 6 lettres
            "AVOCAT", "PATRON", "AUTEUR",
            // 7 lettres
            "POMPIER", "FACTEUR", "BOUCHER",
            // 8 lettres
            "PLOMBIER", "COIFFEUR", "DENTISTE", "ETUDIANT",
            // 9 lettres
            "BOULANGER", "MENUISIER",
            // 10 lettres
            "PROFESSEUR", "DECORATEUR", "INFIRMIERE", "ARCHITECTE",
            // 11 lettres
            "AGRICULTEUR", "VETERINAIRE", "ELECTRICIEN", "JOURNALISTE",
            // 12 lettres
            "INFORMATIQUE",  "RESTAURATEUR"
        ));

        List<String> tousLesMots = new ArrayList<>();
        MOTS_PAR_CATEGORIE.values().forEach(tousLesMots::addAll);
        MOTS_PAR_CATEGORIE.put(CategorieMot.TOUTES, tousLesMots);
    }

    private Dictionnaire() {}

    public static String getMotAleatoire(CategorieMot categorie, int nombreLettres) {
        List<String> motsCategorie = categorie == CategorieMot.TOUTES 
                ? MOTS_PAR_CATEGORIE.get(CategorieMot.TOUTES)
                : MOTS_PAR_CATEGORIE.get(categorie);
                
        if (motsCategorie == null || motsCategorie.isEmpty()) {
            throw new IllegalStateException("Aucun mot disponible pour la catégorie : " + categorie);
        }

        List<String> motsFiltres = new ArrayList<>();
        if (nombreLettres >= 4 && nombreLettres <= 12) {
            motsCategorie.stream()
                .filter(mot -> mot.length() == nombreLettres)
                .forEach(motsFiltres::add);
        } else {
            motsFiltres.addAll(motsCategorie.stream()
                .filter(mot -> mot.length() >= 4 && mot.length() <= 12)
                .collect(Collectors.toList()));
        }

        if (motsFiltres.isEmpty()) {
            throw new IllegalStateException("Aucun mot de " + nombreLettres + 
                    " lettres disponible pour la catégorie : " + categorie);
        }

        Collections.shuffle(motsFiltres, RANDOM);
        return motsFiltres.get(0);
    }

    public static boolean motExiste(String mot) {
        if (mot == null || mot.isEmpty()) {
            return false;
        }
        return MOTS_PAR_CATEGORIE.values().stream()
                .flatMap(List::stream)
                .anyMatch(m -> m.equals(mot));
    }
}
