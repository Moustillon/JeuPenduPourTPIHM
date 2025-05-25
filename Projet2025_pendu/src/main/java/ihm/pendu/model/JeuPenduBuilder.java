package ihm.pendu.model;

/**
 * Builder pour la création d'une partie de pendu.
 */
public class JeuPenduBuilder {
    private CategorieMot categorie = CategorieMot.TOUTES;
    private int nombreLettres = 7;
    private int nbErreursMax = 6;

    /**
     * Constructeur privé - l'instance est créée uniquement via la méthode statique creer()
     */
    private JeuPenduBuilder() {
    }
    /**
     * Méthode statique pour commencer la création d'une partie.
     * @return Une nouvelle instance du builder
     */
    public static JeuPenduBuilder creer() {
        return new JeuPenduBuilder();
    }

    /**
     * Définit la catégorie de mot à utiliser.
     * @param categorie La catégorie souhaitée
     * @return Le builder pour la chaîne de méthodes
     */
    public JeuPenduBuilder avecCategorie(CategorieMot categorie) {
        this.categorie = categorie;
        return this;
    }

    /**
     * Définit le nombre de lettres du mot à deviner.
     * @param nombreLettres Le nombre de lettres (0 pour aléatoire, sinon entre 4 et 12)
     * @return Le builder pour la chaîne de méthodes
     * @throws JeuPenduException Si le nombre de lettres n'est pas valide
     */
    public JeuPenduBuilder avecNombreLettres(int nombreLettres) {
        if (nombreLettres != 0 && (nombreLettres < 4 || nombreLettres > 12)) {
            throw new JeuPenduException(JeuPenduException.TypeErreur.PARAMETRES_INVALIDES, 
                "Le nombre de lettres doit être 0 ou entre 4 et 12");
        }
        this.nombreLettres = nombreLettres;
        return this;
    }

    /**
     * Définit le nombre maximum d'erreurs autorisées.
     * @param nbErreursMax Le nombre d'erreurs maximum (0 pour illimité)
     * @return Le builder pour la chaîne de méthodes
     * @throws JeuPenduException Si le nombre d'erreurs est négatif
     */
    public JeuPenduBuilder avecNbErreursMax(int nbErreursMax) {
        if (nbErreursMax < 0) {
            throw new JeuPenduException(JeuPenduException.TypeErreur.PARAMETRES_INVALIDES, 
                "Le nombre d'erreurs maximum ne peut pas être négatif");
        }
        this.nbErreursMax = nbErreursMax;
        return this;
    }

    // Méthodes d'accès pour la classe JeuPendu
    CategorieMot getCategorie() {
        return categorie;
    }

    int getNombreLettres() {
        return nombreLettres;
    }

    int getNbErreursMax() {
        return nbErreursMax;
    }

    /**
     * Construit une nouvelle partie avec les paramètres spécifiés.
     * @return Une nouvelle instance de JeuPendu
     */
    public JeuPendu construire() {
        return new JeuPendu(this);
    }

}
