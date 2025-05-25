package ihm.pendu.model;

/**
 * Exception levée lors d'une mauvaise utilisation du jeu du pendu.
 */
public class JeuPenduException extends RuntimeException {
    
    /**
     * Types d'erreurs possibles dans le jeu du pendu.
     */
    public enum TypeErreur {
        LETTRE_INVALIDE("La lettre proposée n'est pas valide. Seules les lettres de A à Z sont acceptées."),
        LETTRE_DEJA_PROPOSEE("Cette lettre a déjà été proposée."),
        PARTIE_TERMINEE("La partie est déjà terminée."),
        PARAMETRES_INVALIDES("Les paramètres du jeu sont invalides.");
        
        private final String message;
        
        TypeErreur(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
    }
    
    private final TypeErreur typeErreur;
    
    /**
     * Crée une nouvelle exception avec un type d'erreur prédéfini.
     * @param typeErreur Le type d'erreur
     */
    public JeuPenduException(TypeErreur typeErreur) {
        super(typeErreur.getMessage());
        this.typeErreur = typeErreur;
    }
    
    /**
     * Crée une nouvelle exception avec un type d'erreur et un message personnalisé.
     * @param typeErreur Le type d'erreur
     * @param message Un message détaillé expliquant l'erreur
     */
    public JeuPenduException(TypeErreur typeErreur, String message) {
        super(message);
        this.typeErreur = typeErreur;
    }
    
    /**
     * Retourne le type d'erreur associé à cette exception.
     * @return Le type d'erreur
     */
    public TypeErreur getTypeErreur() {
        return typeErreur;
    }
}
