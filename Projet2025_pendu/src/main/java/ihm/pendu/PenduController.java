package ihm.pendu;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import ihm.pendu.model.CategorieMot;
import ihm.pendu.model.EtatPartie;
import ihm.pendu.model.JeuPendu;
import ihm.pendu.model.JeuPenduBuilder;
import ihm.pendu.model.ResultatProposition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PenduController implements Initializable{

    @FXML
    private TextField lettre;
    @FXML
    private Label motCherch;
    @FXML
    private Label err;
    @FXML
    private Label tabP;
    @FXML
    private Label nbVies;

    JeuPenduBuilder builder;
    JeuPendu jeu;

    private Stage mainFenetre;

    @FXML
    private void actionTriche() {
        Alert triche = new Alert(AlertType.INFORMATION);
        triche.setTitle("Pas bien ça");
        triche.setHeaderText("Le mot est : " + jeu.getMotATrouver());
        triche.showAndWait();
    }

    @FXML
    private void actionGetInfoMot() {
        Alert infoMot = new Alert(AlertType.INFORMATION);
        infoMot.setTitle("Infos mot:");
        infoMot.setHeaderText("Longueur : " + jeu.getMotATrouver().length() + "\nCatégorie : " + jeu.getCategorie());
        infoMot.showAndWait();
    }

    @FXML
    private void actionVersCodeSource() {
    try {
        java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/Moustillon/JeuPenduPourTPIHM"));
    } catch (Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Impossible d'ouvrir le navigateur.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

    public void setMainFenetre(Stage mainFenetre) {
        this.mainFenetre = mainFenetre;
    }

    public String affichMot() {
        if (jeu != null) {
            String mot = new String(jeu.getMotCourant());
            return mot;
        }
        return "";
    }

    private String nbVies() {
    if (jeu != null) {
        int viesRestantes = jeu.getNbErreursMax() - jeu.getNbErreurs();
        int viesTotales = jeu.getNbErreursMax();
        return "Vies restantes : \n" + viesRestantes + " / " + viesTotales;
    }
    return "";
}

    private void jouerTour() {
        if (jeu != null) {
            int viesR = jeu.getNbErreurs();
            if (!lettre.getText().isEmpty()) {
                ResultatProposition result = jeu.proposerLettre(Character.toUpperCase(lettre.getText().toCharArray()[0]));
                if (jeu.getNbErreurs() != viesR) {
                    playSFX("fail.wav");
                } else {
                    playSFX("success.wav");
                }
                affichMsg(result.getMessage());
                motCherch.setText(affichMot());
                lettre.clear();
                nbVies.setText(nbVies());
        } else {
            affichMsg("Donnez une lettre !");
            }
        }
       tabP.setText(dejaJoue());
       if (jeu.isPartieTerminee()) {
            finJeu();
       }
    }

    public void affichMsg(String msg) {
        System.out.println(msg);
        err.setText(msg);
    }

    public void listenerLettre(String newValue, String oldValue) {       
        if (!lettre.getText().isEmpty()) {
            //recupere i
            int i = Character.toUpperCase(lettre.getText().charAt(0)) - 'A';
            //verifier lettre ds tab
            for (int j = 0; j < jeu.getLettresProposees().length; j++) {
                if (jeu.getLettresProposees()[i]) {
                    lettre.clear();
                }
            }
        }       
        if (newValue.length() > 1) {
            lettre.setText(newValue.substring(0, 1));
        }
    }

    public String dejaJoue() {
        StringBuilder lettres = new StringBuilder();
        int count = 0;
    
        for (int i = 0; i < jeu.getLettresProposees().length; i++) {
            if (jeu.getLettresProposees()[i]) {
                if (count > 0) {
                    lettres.append(count % 4 == 0 ? "\n" : " - ");
                }
                lettres.append((char) ('A' + i));
                count++;
            }
        }
    
        return lettres.toString();
    }

    public void finJeu() {
        if (jeu == null) {
            return;
        }
        ButtonType butQuit = new ButtonType("Quitter");
        ButtonType butRejouer = new ButtonType("Rejouer");
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Fini !");
        alert.getButtonTypes().setAll(butRejouer, butQuit);


        if (jeu.getEtatPartie() == EtatPartie.GAGNEE) {
            playSFX("gameClear.wav");
            alert.setHeaderText("Bravo ! Le mot était bien : " + jeu.getMotATrouver());
        } else if (jeu.getEtatPartie() == EtatPartie.PERDUE) {
            playSFX("gameOver.wav");
            alert.setHeaderText("Dommage ! Le mot était : " + jeu.getMotATrouver());
        } 

        Optional<ButtonType> rep = alert.showAndWait();

        if(rep.isPresent()) {
            if (rep.get() == butQuit) {
                mainFenetre.close();
            } else if (rep.get() == butRejouer) {
    GameSettings settings = showSettingsDialog();
    if (settings == null) {
        if (mainFenetre != null) mainFenetre.close();
        return;
    }
    this.builder = JeuPenduBuilder.creer();
    this.builder.avecCategorie(settings.categorie);
    this.builder.avecNombreLettres(settings.lettres);
    this.builder.avecNbErreursMax(settings.vies);
    this.jeu = this.builder.construire();

    motCherch.setText(affichMot());
    err.setText("Joue.");
    tabP.setText("");
    nbVies.setText(nbVies());
    lettre.clear();
        }
    }    
}

    private void playSFX(String fileName) {
    try {
        URL soundURL = getClass().getResource(fileName);
        if (soundURL != null) {
            Media sound = new Media(soundURL.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else {
            System.err.println("Le fichier son '" + fileName + "' n'a pas été trouvé.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private GameSettings showSettingsDialog() {
    Dialog<GameSettings> dialog = new Dialog<>();
    dialog.setTitle("Paramètres du jeu");

    ComboBox<CategorieMot> categorieBox = new ComboBox<>();
    categorieBox.getItems().addAll(
        CategorieMot.JAVA_FX,
        CategorieMot.ANIMAUX,
        CategorieMot.FRUITS,
        CategorieMot.COULEURS,
        CategorieMot.PAYS,
        CategorieMot.METIERS,
        CategorieMot.TOUTES
    );
    categorieBox.setValue(CategorieMot.TOUTES);

    Slider viesSlider = new Slider(0, 10, 6);
    viesSlider.setMajorTickUnit(1);
    viesSlider.setMinorTickCount(0);
    viesSlider.setSnapToTicks(true);
    viesSlider.setShowTickLabels(true);
    viesSlider.setShowTickMarks(true);

    ComboBox<String> lettresBox = new ComboBox<>();
    lettresBox.getItems().add("ANY");
    for (int i = 4; i <= 12; i++) {
        lettresBox.getItems().add(String.valueOf(i));
    }
    lettresBox.setValue("ANY");

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    grid.add(new Label("Catégorie :"), 0, 0);
    grid.add(categorieBox, 1, 0);
    grid.add(new Label("Nombre de vies :"), 0, 1);
    grid.add(viesSlider, 1, 1);
    grid.add(new Label("Nombre de lettres :"), 0, 2);
    grid.add(lettresBox, 1, 2);

    dialog.getDialogPane().setContent(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == ButtonType.OK) {
            CategorieMot cat = categorieBox.getValue() != null ? categorieBox.getValue() : CategorieMot.TOUTES;
            int vies = (int) viesSlider.getValue();
            if (vies == 0) vies = 6;
            int lettres = 0;
            if (!lettresBox.getValue().equals("ANY")) {
                lettres = Integer.parseInt(lettresBox.getValue());
            }
            return new GameSettings(cat, vies, lettres);
        }
        return null;
    });

    Optional<GameSettings> result = dialog.showAndWait();
    return result.orElse(null);
}


private static class GameSettings {
    final CategorieMot categorie;
    final int vies;
    final int lettres;
    GameSettings(CategorieMot categorie, int vies, int lettres) {
        this.categorie = categorie;
        this.vies = vies;
        this.lettres = lettres;
    }
}

    @Override
public void initialize(URL location, ResourceBundle resources) {
    GameSettings settings = showSettingsDialog();
    if (settings == null) {
        if (mainFenetre != null) mainFenetre.close();
        return;
    }
    this.builder = JeuPenduBuilder.creer();
    this.builder.avecCategorie(settings.categorie);
    this.builder.avecNombreLettres(settings.lettres);
    this.builder.avecNbErreursMax(settings.vies);
    this.jeu = this.builder.construire();

    System.out.println(jeu.getMotATrouver());

    motCherch.setText(affichMot());
    err.setText("Joue.");
    tabP.setText("");
    nbVies.setText(nbVies());

    lettre.setOnAction(event -> jouerTour());
    lettre.textProperty().addListener((observable, oldValue, newValue) -> {listenerLettre(newValue, oldValue);});
}
    
}
