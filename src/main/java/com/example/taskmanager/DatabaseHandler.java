package com.example.taskmanager;
import java.sql.*;
//Обработчик базы данных


public class DatabaseHandler extends Task {

    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }
    public void addTask(String Name, String Data, String Description, String Priority, String Subtasks, String Status) {
        try {
            String insert = "INSERT INTO " + Const.TASK_TABLE + "(" + Const.TASK_NAME +
                    "," + Const.TASK_DATA + "," + Const.TASK_DESCRIPTION + "," + Const.TASK_PRIORITY + Const.TASK_SUBTASKS + "," + Const.TASK_STATUS + ")" + "VALUES(?,?,?,?,?,?,?)";
            dbConnection = getDbConnection();
            PreparedStatement prst = dbConnection.prepareStatement(insert);
            prst.setString(1, Name);
            prst.setString(2, Data);
            prst.setString(3, Description);
            prst.setString(4, Subtasks);
            prst.setString(4, Priority);
            prst.setString(4, Status);
            prst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet taskslist(){
        ResultSet resultSet = null;
        String select = "SELECT * FROM taskdb";
        try {
            PreparedStatement prst = getDbConnection().prepareStatement(select);
            resultSet = prst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
