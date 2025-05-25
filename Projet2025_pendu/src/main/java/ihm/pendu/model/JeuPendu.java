package ihm.pendu.model;

import java.util.Arrays;

import ihm.pendu.util.Dictionnaire;

/**
 * Classe principale représentant une partie de pendu.
 */
public class JeuPendu {
    private String motATrouver;
    private final int nbErreursMax;
    private char[] motCourant;
    private boolean[] lettresProposees;
    private int nbErreurs;
    private EtatPartie etat;
    private final CategorieMot categorie;

    /**
     * Constructeur privé - l'instance est créée uniquement via le Builder.
     * @param builder Le builder contenant les paramètres de la partie
     */
    public JeuPendu(JeuPenduBuilder builder) {
        this.categorie = builder.getCategorie();
        this.motATrouver = Dictionnaire.getMotAleatoire(builder.getCategorie(), builder.getNombreLettres());
        this.nbErreursMax = builder.getNbErreursMax();
        this.motCourant = new char[motATrouver.length()];
        this.lettresProposees = new boolean[26]; // Un tableau pour chaque lettre de l'alphabet
        Arrays.fill(motCourant, '-'); // Initialisation avec des tirets
        this.etat = EtatPartie.EN_COURS;
        this.nbErreurs = 0;
    }

    /**
     * Propose une lettre pour le mot.
     * @param lettre La lettre proposée (doit être en majuscule)
     * @return Le résultat de la proposition
     * @throws JeuPenduException si la lettre n'est pas valide, si elle a déjà été proposée, ou si la partie est terminée
     */
    public ResultatProposition proposerLettre(char lettre) {
        if (etat != EtatPartie.EN_COURS) {
            throw new JeuPenduException(JeuPenduException.TypeErreur.PARTIE_TERMINEE);
        }

        // Vérifier si la lettre est valide (A-Z)
        int index = Character.toUpperCase(lettre) - 'A';
        if (index < 0 || index >= 26) {
            throw new JeuPenduException(JeuPenduException.TypeErreur.LETTRE_INVALIDE);
        }

        // Vérifier si la lettre a déjà été proposée
        if (lettresProposees[index]) {
            throw new JeuPenduException(JeuPenduException.TypeErreur.LETTRE_DEJA_PROPOSEE);
        }

        // Marquer la lettre comme proposée
        lettresProposees[index] = true;

        // Vérifier si la lettre est dans le mot
        boolean lettreTrouvee = false;
        for (int i = 0; i < motATrouver.length(); i++) {
            if (motATrouver.charAt(i) == lettre) {
                motCourant[i] = lettre;
                lettreTrouvee = true;
            }
        }

        // Mise à jour de l'état
        if (lettreTrouvee) {
            // Vérifier si le mot est complet
            if (String.valueOf(motCourant).equals(motATrouver)) {
                etat = EtatPartie.GAGNEE;
            }
            return ResultatProposition.BIEN_JOUEE;
        } else {
            nbErreurs++;
            if (nbErreursMax > 0 && nbErreurs >= nbErreursMax) {
                etat = EtatPartie.PERDUE;
            }
            return ResultatProposition.MAUVAIS_CHOIX;
        }
    }

    /**
     * Renvoie l'état actuel de la partie.
     * @return L'état de la partie
     */
    public EtatPartie getEtatPartie() {
        return etat;
    }

    /**
     * Renvoie le mot à trouver (mode triche).
     * @return Le mot à trouver
     */
    public String getMotATrouver() {
        return motATrouver;
    }

    /**
     * Renvoie le mot actuel (lettres trouvées et tirets).
     * @return Le mot actuel
     */
    public char[] getMotCourant() {
        return motCourant.clone();
    }

    /**
     * Renvoie le nombre d'erreurs actuelles.
     * @return Le nombre d'erreurs
     */
    public int getNbErreurs() {
        return nbErreurs;
    }

    /**
     * Renvoie le nombre maximum d'erreurs autorisées.
     * @return Le nombre maximum d'erreurs
     */
    public int getNbErreursMax() {
        return nbErreursMax;
    }

    /**
     * Renvoie un tableau indiquant les lettres déjà proposées.
     * @return Un tableau de 26 booléens (true si la lettre a été proposée)
     */
    public boolean[] getLettresProposees() {
        return lettresProposees.clone();
    }

    /**
     * Renvoie la catégorie du mot à trouver.
     * @return La catégorie du mot
     */
    public CategorieMot getCategorie() {
        return categorie;
    }

    /**
     * Renvoie le nombre de lettres du mot en cours.
     * @return Le nombre de lettres
     */
    public int getNombreLettres() {
        return motATrouver.length();
    }

    /**
     * Vérifie si la partie est terminée.
     * @return true si la partie est terminée, false sinon
     */
    public boolean isPartieTerminee() {
        return etat != EtatPartie.EN_COURS;
    }

    /**
     * Réinitialise la partie avec les mêmes paramètres.
     */
    public void reset() {
        motATrouver = Dictionnaire.getMotAleatoire(categorie, motATrouver.length());
        Arrays.fill(motCourant, '-');
        Arrays.fill(lettresProposees, false);
        nbErreurs = 0;
        etat = EtatPartie.EN_COURS;
    }
}
