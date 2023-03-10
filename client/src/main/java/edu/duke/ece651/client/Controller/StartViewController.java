package edu.duke.ece651.client.Controller;

import edu.duke.ece651.client.Model.LoginModel;
import edu.duke.ece651.client.Model.SignupModel;
import edu.duke.ece651.client.View.MenuView;
import edu.duke.ece651.client.View.SignupView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class StartViewController {
    @FXML
    TextField userName;
    @FXML
    TextField passWord;   // initialized by FMLLoader
    @FXML
    Text error_msg;

    private final LoginModel loginModel;
    private final Stage window;

    public StartViewController(Stage window) {
        this.loginModel = new LoginModel();
        this.window = window;
    }

    /**
     * Start View on click action
     */
    @FXML
    public void clickOnLogin() {
        try {
            // Check the login result from server
            boolean res = loginModel.validateLogin(userName.getText(), passWord.getText());
            if (res) new MenuView().show(this.window, null); // Enter into menu view
            else error_msg.setText("Error in UserName, Password or cannot connect to server. Please try again."); // Show the Error Message
        } catch (IOException e) {
            error_msg.setText("Cannot show Menu view, Please enter again."); // Show the Error Message
        }
    }

    /**
     * Show the signup page when clickOnSignUp
     */
    @FXML
    public void clickOnSignUp(){
        try {
            new SignupView().show(this.window, null);
        } catch (IOException e) {
            error_msg.setText("Cannot show signup view, Please enter again."); // Show the Error Message
        }
    }


}
