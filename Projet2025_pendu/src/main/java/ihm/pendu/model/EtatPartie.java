package ihm.pendu.model;

/**
 * Énumération représentant l'état actuel d'une partie de pendu.
 */
public enum EtatPartie {
    /** La partie est en cours */
    EN_COURS("En cours"),
    
    /** La partie est gagnée */
    GAGNEE("Gagnée"),
    
    /** La partie est perdue */
    PERDUE("Perdue");
    
    private final String libelle;
    
    /**
     * Constructeur de l'énumération.
     * @param libelle Le libellé lisible de l'état
     */
    EtatPartie(String libelle) {
        this.libelle = libelle;
    }
    
    /**
     * Retourne le libellé de l'état.
     * @return Le libellé de l'état
     */
    public String getLibelle() {
        return libelle;
    }
    
    @Override
    public String toString() {
        return libelle;
    }
}
