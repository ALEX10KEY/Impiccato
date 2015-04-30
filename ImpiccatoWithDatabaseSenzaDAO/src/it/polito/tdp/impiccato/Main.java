package it.polito.tdp.impiccato;
	
import it.polito.tdp.impiccato.model.ImpiccatoModel;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader =new FXMLLoader(getClass().getResource("Impiccato.fxml"));
			BorderPane root = (BorderPane)loader.load();
			
			//Model
			ImpiccatoModel model = new ImpiccatoModel();
			
			//Controller
			ImpiccatoController controller = loader.getController();
			controller.setModel(model);
			
			//View
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}