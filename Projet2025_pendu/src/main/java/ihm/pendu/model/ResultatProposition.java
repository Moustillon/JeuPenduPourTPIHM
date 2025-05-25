package ihm.pendu.model;

/**
 * Énumération représentant le résultat d'une proposition de lettre ou de mot.
 */
public enum ResultatProposition {
    /** La lettre est correcte et n'avait pas encore été proposée */
    BIEN_JOUEE("Bien joué !"),
    
    /** La lettre a déjà été proposée précédemment */
    DEJA_PROPOSEE("Vous avez déjà proposé cette lettre."),
    
    /** La lettre n'est pas dans le mot */
    MAUVAIS_CHOIX("Lettre incorrecte."),
    
    /** La partie est déjà terminée */
    PARTIE_TERMINEE("La partie est déjà terminée.");
    
    private final String message;
    
    /**
     * Constructeur de l'énumération.
     * @param message Le message associé au résultat
     */
    ResultatProposition(String message) {
        this.message = message;
    }
    
    /**
     * Retourne le message associé au résultat.
     * @return Le message du résultat
     */
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
