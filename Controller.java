package project4;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField tf_seed;

    @FXML
    void onQuitClick(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void onSet_SeedClick(ActionEvent event) {
    	System.out.println("seed click");
    }

}