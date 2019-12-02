package sk.upjs.miesici.admin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	private static Stage primaryStage;

	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginController controller = new LoginController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
		fxmlLoader.setController(controller);
		Parent parent = fxmlLoader.load();
		Scene scene = new Scene(parent);
		App.primaryStage = primaryStage;
		primaryStage.getIcons().add(new Image("https://www.tailorbrands.com/wp-content/uploads/2019/04/Artboard-5-copy-13xxhdpi.png"));
        primaryStage.setMinHeight(196);
        primaryStage.setMinWidth(600);
        primaryStage.setResizable(false);
		primaryStage.setTitle("Prihlásenie");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void switchScene(Object controller, String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
            fxmlLoader.setController(controller);

            Parent parentPane = fxmlLoader.load();
            primaryStage.getScene().setRoot(parentPane);
        } catch (IOException e) {
            System.err.println("Failed switching to scene: " + fxmlPath + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
