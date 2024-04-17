package tn.esprit.controllers;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.Event;

public class SingleEvent implements Initializable {
    @FXML
    private ImageView afficheIv;
    @FXML
    private Text titleText;
    @FXML
    private Text startDateText;
    @FXML
    private Button moreInfoBtn;

    private Event eventInfo;
    @FXML
    private Text catText;
    @FXML
    private Text locText;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficheIv.maxWidth(202);
        afficheIv.maxHeight(114);
    }

    public void setData(Event e){
        titleText.setText(e.getEventName());
        catText.setText(e.getCategory().getName());
        startDateText.setText(String.valueOf(e.getDate()));
        locText.setText(e.getLocation());



        File imageFile = new File(e.getImage());
        Image image = new Image(imageFile.toURI().toString());
        afficheIv.setImage(image);
        afficheIv.setPreserveRatio(true);
        //Save event Id:
        eventInfo = e;
    }

    @FXML
    private void showMoreInfo(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventInfo.fxml"));
            Parent root = loader.load();
            EventInfo controller = loader.getController();
            controller.sendEvent(eventInfo);

            // Get the scene from the current event's source node
            Scene scene = ((Node) event.getSource()).getScene();
            if (scene != null) {
                // Set the root of the scene to the loaded FXML content
                scene.setRoot(root);
            } else {
                System.out.println("Error: Scene is null.");
            }
        } catch (IOException ex) {
            System.out.println("Error loading EventInfo.fxml: " + ex.getMessage());
        }

    }
}
