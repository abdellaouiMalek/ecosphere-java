package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.History;
import tn.esprit.models.Object;
import tn.esprit.services.ServiceHistory;
import tn.esprit.services.ServiceObject;
import tn.esprit.util.DBconnection;

import javax.naming.Name;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
public class HistoryController  {
    ObservableList<History> HistoryList = FXCollections.observableArrayList();
    ServiceHistory history = new ServiceHistory();
    Connection cnx;
    Object object;
    @FXML
    private Label label;
    @FXML
    private Button Histiory_btn;

    @FXML
    private AnchorPane Histiory_form;

    @FXML
    private TableView<History> Histiory_tableView;

    @FXML
    private AnchorPane add_Histiory;

    @FXML
    private Button add_btn;

    @FXML
    private DatePicker createdat;

    @FXML
    private Button delete_btn;

    @FXML
    private Button menu_btn;

    @FXML
    private ComboBox<String> sharinghub_intcond;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_name;

    @FXML
    private Button update_btn;

    @FXML
    private Label username;

    @FXML
    private TableColumn<History, String> Histiory_col_name;
    @FXML
    private TableColumn<History, String> Histiory_col_initialcond;
    @FXML
    private TableColumn<History, String> Histiory_col_createdat;


    private History HistoryData;
    private final ServiceHistory ps = new ServiceHistory();
    private PreparedStatement prepare;
    private ResultSet result;
    public void initialize( Object object) {
        this.object=object;


        Histiory_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Histiory_col_initialcond.setCellValueFactory(new PropertyValueFactory<>("initialCondition"));
        Histiory_col_createdat.setCellValueFactory(new PropertyValueFactory<>("date"));

        Histiory_tableView.getColumns().addAll(Histiory_col_name, Histiory_col_initialcond, Histiory_col_createdat);

        initialconditionList();


        refreshTable();

    }
    // Method to receive histories list
    public void setHistories(List<History> histories) {
        // Process histories list as needed
        // Example: Display histories in the label
        StringBuilder sb = new StringBuilder();
        for (History history : histories) {
            sb.append(history.getName()).append(", "); // Example: Concatenate history names
        }
        label.setText(sb.toString());
    }


    private ObservableList<History> sharinghubListData;

    @FXML
    void add_btn() {
        try {
            String name = tf_name.getText();
            String initialCondition = sharinghub_intcond.getSelectionModel().getSelectedItem();
            if (name.isEmpty() || initialCondition.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields ");
                return;
            }
            History newHistory = new History();
            newHistory.setName(name);
            newHistory.setInitialCondition(initialCondition);
            newHistory.setDate(new Date());
            newHistory.setObject_id(object.getId());

            ServiceHistory newpostService = new ServiceHistory();
            newpostService.add(newHistory);
            // Ajoutez cette ligne pour récupérer l'ID auto-incrémenté après l'ajout dans la base de données
            int newId = newHistory.getId();
            HistoryList.add(newHistory);
            newHistory.setId(newId); // Mettez à jour l'ID dans l'objet Publication avec l'ID auto-incrémenté
            refreshTable();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Publication added successfully.");
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the publication.");
        }
    }

    private void clearFields() {
        tf_name.clear();
        sharinghub_intcond.getSelectionModel().clearSelection();

    }


    private String[] initialcondtList = {"Excellent", "Good", "Bad", "Medium"};

    public void initialconditionList() {
        List<String> initcondList = new ArrayList<>();

        for (String data : initialcondtList) {
            initcondList.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(initcondList);
        sharinghub_intcond.setItems(listData);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void update_btn() {
        History selectedHistory = Histiory_tableView.getSelectionModel().getSelectedItem();
        if (selectedHistory != null) {
            try {
                String name = tf_name.getText();
                String initialCndition = (String) sharinghub_intcond.getSelectionModel().getSelectedItem();

                if (name.isEmpty() || initialCndition.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                    return;
                }
                selectedHistory.setName(name);
                selectedHistory.setInitialCondition(initialCndition);

                ServiceHistory history = new ServiceHistory();
                history.update(selectedHistory);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Post updated successfully.");
                clearFields();
                refreshTable();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the post.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a post to update.");
        }
    }
    @FXML
    void delete_btn(ActionEvent event) {
        History selectedHistory = Histiory_tableView.getSelectionModel().getSelectedItem();
        if (selectedHistory != null) {
            try {
                ServiceHistory HistoryServices = new ServiceHistory();
                HistoryServices.delete(selectedHistory.getId());
                HistoryList.remove(selectedHistory); // Supprimez également la publication de votre liste observable
                refreshTable();

                showAlert(Alert.AlertType.INFORMATION, "Success", "history deleted successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the history.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a history to delete.");
        }

    }




    @FXML
    void onTableRowClicked(MouseEvent event) throws IOException {
        History Hi = Histiory_tableView.getSelectionModel().getSelectedItem();
        tf_name.setText(Hi.getName());
        sharinghub_intcond.setValue(Hi.getInitialCondition());



    }

    public void sharinghubSelectData(){
        History his = Histiory_tableView.getSelectionModel().getSelectedItem();
        int num = Histiory_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1)< -1) return;

        tf_name.setText(his.getName());
        sharinghub_intcond.setValue(his.getInitialCondition());
        //createdat.setValue(his.getDate());


    }
    @FXML
    void back_btn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MyTest.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        Histiory_tableView.getItems().clear();
        ServiceObject so =new ServiceObject();
        List<History> h = so.getAllHistoryByObject(object.getId());
        HistoryList = FXCollections.observableArrayList(h);

        Histiory_tableView.setItems(HistoryList);
    }
}
