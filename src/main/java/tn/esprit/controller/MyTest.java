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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Object;
import tn.esprit.services.ServiceObject;
import tn.esprit.test.MainFX;
import tn.esprit.util.DBconnection;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;


public class MyTest implements Initializable {
    ObservableList<Object> ObjectList = FXCollections.observableArrayList();
    ServiceObject o = new ServiceObject();
    Connection cnx;

    @FXML
    private AnchorPane add_O;

    @FXML
    private Button logout_btn;

    @FXML
    private Button menu_btn;

    @FXML
    private TableView<Object> sharing_tableView;

    @FXML
    private AnchorPane sharinghub_ImageView;

    @FXML
    private Button sharinghub_addBtn;

    @FXML
    private TextField sharinghub_age;

    @FXML
    private Button sharinghub_btn;

    @FXML
    private Button sharinghub_clearBtn;

    @FXML
    private TableColumn<Object, Integer> sharinghub_col_age;

    @FXML
    private TableColumn<Object, String> sharinghub_col_description;

    @FXML
    private TableColumn<Object, Integer> sharinghub_col_id;

    @FXML
    private TableColumn<Object, String> sharinghub_col_name;

    @FXML
    private TableColumn<Object, Float> sharinghub_col_price;

    @FXML
    private TableColumn<Object, String> sharinghub_col_type;

    @FXML
    private Button sharinghub_deleteBtn;

    @FXML
    private ComboBox<String> sharinghub_description;

    @FXML
    private AnchorPane sharinghub_form;

    @FXML
    private TextField sharinghub_id;

    @FXML
    private ImageView sharinghub_imageView;

    @FXML
    private Button sharinghub_importBtn;

    @FXML
    private TextField sharinghub_name;

    @FXML
    private TextField sharinghub_price;


    @FXML
    private ComboBox<String> sharinghub_type;

    @FXML
    private Button sharinghub_updateBtn;

    @FXML
    private Label username;
    private Object ObjData;
    private File file;
    private File picture;
    private final ServiceObject ps = new ServiceObject();

    public MyTest() {
        cnx = DBconnection.getInstance().getCnx();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize TableColumn instances
        TableColumn<Object, String> sharinghub_col_name = new TableColumn<>("Name");
        TableColumn<Object, String> sharinghub_col_type= new TableColumn<>("Type");
        TableColumn<Object, String> sharinghub_col_description = new TableColumn<>("Description");
        TableColumn<Object, Integer> sharinghub_col_age = new TableColumn<>("Age");
        TableColumn<Object, Float> sharinghub_col_price = new TableColumn<>("price");
        //imageColumn = new TableColumn<>("Image");
        // Set cell factory for the image column to use ImageCell
//        imageColumn.setCellFactory(column -> new ImageCell());

        // Bind TableView columns to Publication properties
        sharinghub_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        sharinghub_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        sharinghub_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        sharinghub_col_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        sharinghub_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        // add columns
        sharing_tableView.getColumns().addAll(sharinghub_col_name, sharinghub_col_type, sharinghub_col_description,sharinghub_col_age, sharinghub_col_price);
        // fill table

        sharinghubTypeList();
        sharinghubDescriptionList();
        sharinghubShowData();

        refreshTable();
    }
    @FXML
    void importBtn() {
        Stage stage = (Stage) sharinghub_imageView.getScene().getWindow(); // Obtenir le Stage actuel
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            picture = new File(file.toURI().toString()); // Stocker le chemin de l'image
            sharinghub_imageView.setImage(new Image(String.valueOf(picture)));
        }
   //     FileChooser openFile = new FileChooser();
    //    openFile.setTitle("Open Image File");
      //  openFile.getExtensionFilters().addAll(
        //        new FileChooser.ExtensionFilter("Open Picture File", ".png", ".jpg"));
        //File file = openFile.showOpenDialog(add_O.getScene().getWindow());
        //if (file != null) {
          //  try {
            //    String picture = file.toURI().toString(); // Stocker le chemin de l'image
                // Load image into the sharinghub_imageView
              //   image = new Image(picture);
                //sharinghub_imageView.setImage(picture);

            //} catch (Exception e) {
             //   e.printStackTrace();
            //}
       // }

    }



    public ObservableList<Object> sharinghubDataList() {
        ObservableList<Object> listData = FXCollections.observableArrayList();
        String sql = "SElECT * FROM object ";
        //connect = MainFX.main();
        connect=database.DBconnection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            Object Obj;
            while (result.next()) {
                ObjData = new Object(result.getInt("age")
                        , result.getString("type")
                        , result.getString("picture")
                        , result.getString("description")
                        , result.getString("name")
                        , result.getFloat("price"));
                listData.add(ObjData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    // TO SHOW DATA ON OUR TABLE
    private ObservableList<Object> sharinghubListData;



    @FXML
    void addBtn() {
        try {
        String name = sharinghub_name.getText();
        String type = sharinghub_type.getSelectionModel().getSelectedItem() ;
        String description = sharinghub_description.getSelectionModel().getSelectedItem();
        int age = Integer.parseInt(sharinghub_age.getText());
        Float price = Float.valueOf(sharinghub_price.getText());
        //Image picturee = sharinghub_ImageView.getPicture();

            if (sharinghub_name.getText().isEmpty()
                    || sharinghub_type.getSelectionModel().getSelectedItem() == null
                    || sharinghub_description.getSelectionModel().getSelectedItem() == null
                    || sharinghub_age.getText().isEmpty()
                    ||sharinghub_price.getText().isEmpty()) {
                showAlert("Error Message", "Please fill all blank fields",Alert.AlertType.ERROR );
                return;
            }

//        String picture = savePictureToFile(picturee);

        Object o = new Object(age,type,"waffu", description,name ,price); // Création d'un produit avec l'ID du partenaire
            o.setName(name);
            o.setType(type);
            o.setDescription(description);
            o.setAge(age);
            o.setPrice(price);

            //o.setPicture(picturee); // Utilisez setImageObject au lieu de setImage

        ServiceObject service = new ServiceObject();
        service.add(o); // Ajouter le produit à la base de données
// Ajoutez cette ligne pour récupérer l'ID auto-incrémenté après l'ajout dans la base de données
            int newId = o.getId();
            ObjectList.add(o);
            refreshTable();
            o.setId(newId); // Mettez à jour l'ID dans l'objet Publication avec l'ID auto-incrémenté

       //     publicationList.add(o);

        showAlert("Success", "Object added successfullys!", Alert.AlertType.INFORMATION);
            clearFields();

    } catch (NumberFormatException e) {
        showAlert("Erreur de Format", "Please fill all blank fields .", Alert.AlertType.ERROR);
    } catch (Exception e) {
        showAlert("Erreur", "Une erreur s'est objet lors de l'ajout du objet: " + e.getMessage(), Alert.AlertType.ERROR);
    }
        }
    private void clearFields() {
        sharinghub_name.clear();
        sharinghub_type.getSelectionModel().clearSelection();
        sharinghub_description.getSelectionModel().clearSelection();
        sharinghub_age.setText("");
        sharinghub_price.setText("");
        //    sharinghub_ImageView.setImage(null);
    }
  /*
    private String savePictureToFile(Image picture) {
        String imagePath = ""; // Chemin de l'image enregistrée
        try {

            String fileName = "image_" + System.currentTimeMillis() + ".png";


            String currentDir = System.getProperty("user.dir");


            imagePath = currentDir + File.separator + fileName;


            File outputFile = new File(imagePath);


        //    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
         //   ImageIO.write(bufferedImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }**/
    private void showAlert(String title, String message, Alert.AlertType alertType) {
       Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void updateBtn() {
        Object selectedObject = sharing_tableView.getSelectionModel().getSelectedItem();
        if(selectedObject != null) {
            try {
                String name = sharinghub_name.getText();
                String type = sharinghub_type.getSelectionModel().getSelectedItem();
                String description = sharinghub_description.getSelectionModel().getSelectedItem();
                int age = Integer.parseInt(sharinghub_age.getText());
                float price = Float.parseFloat(sharinghub_price.getText());
                if (name.isEmpty() || type.isEmpty() || description.isEmpty() || sharinghub_age.getText().isEmpty() || sharinghub_price.getText().isEmpty()) {
                    showAlert("Error", "Please fill in all fields.", Alert.AlertType.ERROR );
                    return;
                }
                //      if (image != null) {
                //        String imagePath = saveImageToFile(image);
                //      selectedPublication.setImageObject(image);
                //}
                Float priceObject = price; // Convertir le float en Float
                Integer ageObject = age;   // Convertir l'int en Integer

                // Mettre à jour les propriétés de l'objet sélectionné

                selectedObject.setName(name);
                selectedObject.setType(type);
                selectedObject.setDescription(description);
                selectedObject.setAge(age);
                selectedObject.setPrice(price);
                ServiceObject service = new ServiceObject();
                service.update(selectedObject);

                showAlert( "Success", "Publication updated successfully.",Alert.AlertType.INFORMATION);

                clearFields();
                refreshTable();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while updating the Object.",Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert( "Error", "Please select a Object to update.",Alert.AlertType.ERROR);
        }
    }





    @FXML
    void clearBtn() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to clear the table?");


        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                clearFields();
                ObjectList.clear();
                refreshTable();
                showAlert( "Success", "Table cleared successfully.",Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
                showAlert( "Error", "An error occurred while clearing the fields.",Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    void deleteBtn()  {
        try {
            Object selectedObject = sharing_tableView.getSelectionModel().getSelectedItem();
            if(selectedObject != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this publication?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {

                    ServiceObject service = new ServiceObject();
                    service.delete(selectedObject.getId());
                    ObjectList.remove(selectedObject);
                    showAlert( "Success", "Publication deleted successfully.",Alert.AlertType.INFORMATION);
                    clearFields();
                    refreshTable();
                }
            } else {
                showAlert( "Error", "Please select a publication to delete.",Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert( "Error", "An error occurred while deleting the publication.",Alert.AlertType.ERROR);
        }
    }

    //LETS MAKE A BEHAVIOR FOR IMPORT BTN FIRST







    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private String image;

//MERGE ALL DATAS

    public void sharinghubShowData() {
        sharinghubListData = sharinghubDataList();
        sharinghub_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        sharinghub_col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sharinghub_col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        sharinghub_col_description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        sharinghub_col_age.setCellValueFactory(new PropertyValueFactory<>("Age"));
        sharinghub_col_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        sharing_tableView.setItems(sharinghubListData);
    }
// Methode pour l affichage de image de Objet selectioné

    public void sharinghubSelectData(){
        Object obj = sharing_tableView.getSelectionModel().getSelectedItem();
        int num = sharing_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1)< -1) return;

      // sharinghub_id.setText(obj.getId());
        sharinghub_name.setText(obj.getName());
        sharinghub_age.setText(String.valueOf(obj.getAge()));
        sharinghub_price.setText(String.valueOf((obj.getPrice())));
        sharinghub_description.setValue(obj.getDescription());
        sharinghub_type.setValue(obj.getType());

        String path ="file:"+obj.getPicture();
       // data.date = String.valueOf(obj.getDate());
       // data.id = obj.getId();

        //    sharinghub_ImageView.setImage(path,160, 146, false, true);

    }

    private String[] typeList = {"HouseWare", "Accessory", "Machine"};

    public void sharinghubTypeList() {
        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        sharinghub_type.setItems(listData);
    }

    private String[] descriptionList = {"Excellent", "Good", "Bad", "Medium"};

    public void sharinghubDescriptionList() {
        List<String> descriptionL = new ArrayList<>();

        for (String data : descriptionList) {
            descriptionL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(descriptionL);
        sharinghub_description.setItems(listData);
    }

    //partie 2
    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message ");
            alert.setHeaderText(null);
            alert.setContentText("are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //TO HIDE MAIN FROM
                logout_btn.getScene().getWindow().hide();
                //LINK YOUR LOGIN FROM AND SHOW IT
                Parent root = FXMLLoader.load(getClass().getResource("MyTest.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("EcoSphére System");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void refreshTable() {
            sharing_tableView.getItems().clear();

            // Ajouter tous les éléments de ObjectList à la TableView


            List<Object> objects = o.getAll();
            ObjectList = FXCollections.observableArrayList(objects);
            sharing_tableView.setItems(ObjectList);
        }

    }

