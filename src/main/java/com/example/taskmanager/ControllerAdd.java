package com.example.taskmanager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerAdd {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField dataText;

    @FXML
    private TextArea descriptionText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priorityText;

    @FXML
    private TextField statusText;

    @FXML
    private TextField subtasksText;

    //Окно добавления груза
    @FXML
    void initialize() {
        DatabaseHandler dbhandler = new DatabaseHandler();
        //Кнопка "Добавить"
        addButton.setOnAction(event -> {
            dbhandler.addTask(nameText.getText(), dataText.getText(), descriptionText.getText(),
                    priorityText.getText(), subtasksText.getText(), statusText.getText());
            addButton.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Список задач ");
                stage.setScene(new Scene(root, 600, 486));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //Кнопка "Назад"
        backButton.setOnAction(actionEvent -> {backButton.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Список задач");
                stage.setScene(new Scene(root, 600, 486));
                MainController.llist.add(String.valueOf(MainController.identificator + 1));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }});
    }
}