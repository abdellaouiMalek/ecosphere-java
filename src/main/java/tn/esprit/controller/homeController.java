package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class homeController {

    @FXML
    private Button logout_btn;

    @FXML
    private Button menu_btn;

    @FXML
    private AnchorPane mytest;

    @FXML
    private TableView<?> sharing_tableView;

    @FXML
    private TableView<?> sharing_tableView1;

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
    private TableColumn<?, ?> sharinghub_col_age;

    @FXML
    private TableColumn<?, ?> sharinghub_col_age1;

    @FXML
    private TableColumn<?, ?> sharinghub_col_description;

    @FXML
    private TableColumn<?, ?> sharinghub_col_description1;

    @FXML
    private TableColumn<?, ?> sharinghub_col_id;

    @FXML
    private TableColumn<?, ?> sharinghub_col_id1;

    @FXML
    private TableColumn<?, ?> sharinghub_col_name;

    @FXML
    private TableColumn<?, ?> sharinghub_col_name1;

    @FXML
    private TableColumn<?, ?> sharinghub_col_price;

    @FXML
    private TableColumn<?, ?> sharinghub_col_price1;

    @FXML
    private TableColumn<?, ?> sharinghub_col_type;

    @FXML
    private TableColumn<?, ?> sharinghub_col_type1;

    @FXML
    private Button sharinghub_deleteBtn;

    @FXML
    private ComboBox<?> sharinghub_description;

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
    private ComboBox<?> sharinghub_type;

    @FXML
    private Button sharinghub_updateBtn;

    @FXML
    private Label username;

    @FXML
    void addBtn(ActionEvent event) {
        System.out.println("ààààààààààààààààààààààààààààà");
    }

    @FXML
    void clearBtn(ActionEvent event) {

    }

    @FXML
    void deleteBtn(ActionEvent event) {

    }

    @FXML
    void importBtn(ActionEvent event) {

    }

    @FXML
    void updateBtn(ActionEvent event) {

    }

}
