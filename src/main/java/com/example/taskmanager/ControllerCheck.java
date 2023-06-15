package com.example.taskmanager;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerCheck {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    public Text dateallowText;

    @FXML
    public Text datedeclaimedText;

    @FXML
    public Text locationText;

    @FXML
    public Text nameText;
    //Окно проверки информации
    @FXML
    void initialize() {
        try {
            toDisplay();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Кнопка "Назад"
        backButton.setOnAction(actionEvent -> {backButton.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Проверка грузов");
                stage.setScene(new Scene(root, 600, 486));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }});
    }
    //Метод отображения информации
    public void toDisplay() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cargobd", "root","123");
        String ch = String.format("select * from cargo WHERE id='%s'", MainController.llist.get(MainController.id));
        ResultSet rs = connection.createStatement().executeQuery(ch);
        while (rs.next()) {
            nameText.setText(rs.getString(2));
            dateallowText.setText(rs.getString(3));
            datedeclaimedText.setText(rs.getString(4));
            locationText.setText(rs.getString(5));
        }
    }
}