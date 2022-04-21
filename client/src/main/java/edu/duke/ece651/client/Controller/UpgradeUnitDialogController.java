package edu.duke.ece651.client.Controller;


import edu.duke.ece651.client.Model.GameModel;
import edu.duke.ece651.client.View.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;


public class UpgradeUnitDialogController implements Initializable,Communication {
    @FXML
    Text terrName,lv0_n,lv1_n,lv2_n,lv3_n,lv4_n,lv5_n,lv6_n,spy_n;
    @FXML
    ChoiceBox<String> selectLvFrom,selectLvTo;
    @FXML
    ChoiceBox<Integer> selectNum;
    @FXML
    Text cost_t;
    @FXML
    Pane mapPane;

    private final Stage window;
    private final boolean debug;
    private final int n_player;
    private final ObservableList<String> toList;
    private final ObservableList<String> fromList;
    private final ObservableList<Integer> numList;
    private String clickTerr;


    public UpgradeUnitDialogController(Stage window, boolean debug){
        this.window = window;
        this.debug = debug;
        this.n_player = GameModel.getInstance().getClientPlayerPacket().getNumOfPlayers();;
        this.toList = FXCollections.observableArrayList();
        this.fromList = FXCollections.observableArrayList();
        this.numList = FXCollections.observableArrayList();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        terrName.setText("");
        lv0_n.setText("");
        lv1_n.setText("");
        lv2_n.setText("");
        lv3_n.setText("");
        lv4_n.setText("");
        lv5_n.setText("");
        lv6_n.setText("");
        spy_n.setText("");
        cost_t.setText("Unknown");
        selectLvTo.setItems(toList);
        selectLvFrom.setItems(fromList);
        selectNum.setItems(numList);

        //set map
        try {
            mapPane.getChildren().add(new MapView(null,debug).loadMap(n_player, this, "upgradeUnitDialogView"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void clickOnConfirm(ActionEvent actionEvent) {
        if(!GameModel.getInstance().doUpgradeUnit(new String[]{this.clickTerr, selectLvFrom.getValue() , "1", selectLvTo.getValue()}, debug)){
            System.out.println("Invalid value (Server check)");
        }
        else {
            String record = "Upgrade Level " + selectLvFrom.getValue() + " to " + selectLvTo.getValue() + " from " + this.clickTerr;
            System.out.println(record);
            window.close();
        }
    }

    @FXML
    public void clickOnGetCost(ActionEvent actionEvent) {
        cost_t.setText("NA");
    }

    @Override
    public void setTerrInfo(String clickTerr) {

        numList.clear();
        toList.clear();

        terrName.setText(clickTerr);

        // Set clickTerr
        this.clickTerr = clickTerr;

        // Get My Terr Info
        if(GameModel.getInstance().getMyTerrList().contains(clickTerr)){
            ArrayList<Integer> unitNumList = new ArrayList<>();
            GameModel.getInstance().getTerrUnits(clickTerr, unitNumList);

            lv0_n.setText(String.valueOf(unitNumList.get(0)));
            lv1_n.setText(String.valueOf(unitNumList.get(1)));
            lv2_n.setText(String.valueOf(unitNumList.get(2)));
            lv3_n.setText(String.valueOf(unitNumList.get(3)));
            lv4_n.setText(String.valueOf(unitNumList.get(4)));
            lv5_n.setText(String.valueOf(unitNumList.get(5)));
            lv6_n.setText(String.valueOf(unitNumList.get(6)));
            spy_n.setText("NA");


            // Set from level list
            for (int i = 1; i < 7; i++) {
                this.fromList.add(String.valueOf(i));
                this.toList.add(String.valueOf(i));
            }
            this.numList.add(1);

        }
        // else if in the cache
        else if(GameModel.getInstance().getLocalEnemyTerrs().containsKey(clickTerr)){
            // Get the units number
            ArrayList<Integer> unitNumList = GameModel.getInstance().getLocalEnemyTerrs().get(clickTerr);
            lv0_n.setText(String.valueOf(unitNumList.get(0)));
            lv1_n.setText(String.valueOf(unitNumList.get(1)));
            lv2_n.setText(String.valueOf(unitNumList.get(2)));
            lv3_n.setText(String.valueOf(unitNumList.get(3)));
            lv4_n.setText(String.valueOf(unitNumList.get(4)));
            lv5_n.setText(String.valueOf(unitNumList.get(5)));
            lv6_n.setText(String.valueOf(unitNumList.get(6)));
            spy_n.setText("NA");

        }
        // Inviable
        else{
            lv0_n.setText("NA");
            lv1_n.setText("NA");
            lv2_n.setText("NA");
            lv3_n.setText("NA");
            lv4_n.setText("NA");
            lv5_n.setText("NA");
            lv6_n.setText("NA");
            spy_n.setText("NA");
        }
    }
}
