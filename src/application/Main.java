package application;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		try {
			HBox homePage = (HBox)FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			Scene scene = new Scene(homePage);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("I-Ancing : Personal Financing");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/view/logonobg.png")));

			CommonObjs commonObjs = CommonObjs.getInstance();
			commonObjs.setHomePage(homePage);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
