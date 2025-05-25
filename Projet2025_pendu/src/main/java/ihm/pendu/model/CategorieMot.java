package ihm.pendu.model;

/**
 * Énumération représentant les différentes catégories de mots disponibles dans le jeu.
 */
public enum CategorieMot {
    /** Catégorie pour les termes liés à JavaFX */
    JAVA_FX("JavaFX"),
    
    /** Catégorie pour les noms d'animaux */
    ANIMAUX("Animaux"),
    
    /** Catégorie pour les noms de fruits */
    FRUITS("Fruits"),
    
    /** Catégorie pour les noms de couleurs */
    COULEURS("Couleurs"),
    
    /** Catégorie pour les noms de pays */
    PAYS("Pays"),
    
    /** Catégorie pour les noms de métiers */
    METIERS("Métiers"),
    
    /** Catégorie spéciale qui peut contenir n'importe quelle catégorie */
    TOUTES("Toutes les catégories");
    
    private final String libelle;
    
    /**
     * Constructeur de l'énumération.
     * @param libelle Le libellé lisible de la catégorie
     */
    CategorieMot(String libelle) {
        this.libelle = libelle;
    }
    
    /**
     * Retourne le libellé de la catégorie.
     * @return Le libellé de la catégorie
     */
    public String getLibelle() {
        return libelle;
    }
    
    @Override
    public String toString() {
        return libelle;
    }
}
