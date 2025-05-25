package ihm.pendu; 


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PenduFXapp extends Application {

	public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PenduFXapp.class.getResource("Pendu.fxml"));
            BorderPane root = loader.load();
            PenduController ctrl = loader.getController();

            ctrl.setMainFenetre(primaryStage);

            Scene scene = new Scene(root, 800, 600);
            String css = PenduFXapp.class.getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.setTitle("IHM 2025");
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Ressource FXML inconnue.");
            e.printStackTrace();
            System.exit(1);
        }
        
    }

    public void showPendu() {
        
    }

    public static void main2(String[] args) {
        Application.launch(args);
    }

}