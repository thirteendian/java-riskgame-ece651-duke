package edu.duke.ece651.client.View;

import edu.duke.ece651.client.Controller.UpgradeUnitDialogController;
import edu.duke.ece651.client.Model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class UpgradeUnitDialogView implements View{
    @Override
    public void show(Stage window, Model model) throws IOException {
        // load start view fxml
        URL xmlResource = getClass().getResource("/xml/upgradeUnitDialog.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);

        // use loader setControllerFactory to specify how to create controllers.
        HashMap<Class<?>,Object> controllers = new HashMap<>();
        controllers.put(UpgradeUnitDialogController.class, new UpgradeUnitDialogController(window));
        loader.setControllerFactory(controllers::get);
        GridPane gp = loader.load();

        // create scene and load css
        Scene scene = new Scene(gp, 640, 480);
        URL cssResource = getClass().getResource("/css/button.css");
        scene.getStylesheets().add(cssResource.toString());

        window.setScene(scene);
        window.showAndWait();
    }
}
