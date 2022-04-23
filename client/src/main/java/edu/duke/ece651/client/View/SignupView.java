package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.SignUpViewController;
import edu.duke.ece651.client.Model.Model;
import edu.duke.ece651.client.SceneCollector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SignupView implements View{
    @Override
    public void show(Stage window, Model model, boolean debug) throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/signUpView.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(SignUpViewController.class, new SignUpViewController(window, debug));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());
        SceneCollector.signUpView = scene;

        window.setScene(scene);
        window.show();
        }

}
