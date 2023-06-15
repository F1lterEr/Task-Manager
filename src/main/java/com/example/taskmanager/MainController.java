package com.example.taskmanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button EditButton;

    @FXML
    private Button InfoButton;
    public static int identificator = 0;
    public static ObservableList<String> llist = FXCollections.observableArrayList();
    public static int id = 0;

    @FXML
    public ListView<String> taskslist;
    ObservableList<String> list = FXCollections.observableArrayList();
    //Главное окно
    @FXML
    void initialize() throws Exception{
        //Метод отображения груза в списке
        toDisplay();
        //Кнопка "Информация о грузе"
        InfoButton.setOnAction(actionEvent -> {InfoButton.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("CheckingInformation.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Информация о грузе");
                stage.setScene(new Scene(root, 600, 486));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }});
        //Кнопка "Редактировать"
        EditButton.setOnAction(actionEvent -> {EditButton.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("EditingInformation.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Редактирование информации");
                stage.setScene(new Scene(root, 600, 486));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }});
        //Кнопка "Добавить"
        AddButton.setOnAction(actionEvent -> {AddButton.getScene().getWindow().hide();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("AddInformation.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOpacity(1);
                stage.setTitle("Добавление задачи");
                stage.setScene(new Scene(root, 600, 486));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }});
        //Кнопка "Удалить"
        DeleteButton.setOnAction(actionEvent -> {
            try {
                toDelete(id);
                toDisplay();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    //Метод отображения груза в списке
    public void toDisplay() throws Exception {
        list.clear();
        taskslist.setItems(list);
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb", "root","123");
        ResultSet rs = connection.createStatement().executeQuery("select * from tasks");
        llist.clear();
        while (rs.next()) {
            list.add(rs.getString(2));
            identificator=rs.getInt(1);
            llist.add(String.valueOf(identificator));
        }
        taskslist.setItems(list);
    }
    //Метод удаления груза из списка
    public void toDelete(int id) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb", "root","123");
        String sql = String.format("DELETE from tasks WHERE id='%s'", id);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        toDisplay();
    }
    //Метод выбора груза из списка
    public void getSelected(MouseEvent mouseEvent) {
        try{
            int index = taskslist.getSelectionModel().getSelectedIndex();
            if(index < -1){
                return;
            }
            else{
                int idbd = Integer.parseInt(taskslist.getItems().get(index));
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskdb", "root","123");
                String ch = String.format("select id from tasks WHERE Name='%s'", idbd);
                ResultSet rs = connection.createStatement().executeQuery(ch);
                while (rs.next()){
                    int ch2 = rs.getInt("id");
                    id=ch2;
                }
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
}