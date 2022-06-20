/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ifte
 */
public class DatabaseConnect {

    private final String MYSQL_URL;
    private final String DB_URL;
    private final String USERNAME;
    private final String PASSWORD;
    private Connection sqlConnection;
    private static Connection dbConnection;
    private Statement st;
    private PreparedStatement createSHMSDB;
    private PreparedStatement createTableBuilding;
    private PreparedStatement createTableRoom;
    private PreparedStatement createTableMaintenanceItem;
    private PreparedStatement createTableStudent;
    private PreparedStatement createTableManagement;
    private PreparedStatement createTableEmployee;
    private PreparedStatement createTableMaintenance;
//    static PreparedStatement insertBuilding;
//    static PreparedStatement insertRoom;
//    static PreparedStatement insertItem;
//    static PreparedStatement insertStudent;
//    static PreparedStatement insertManagement;
//    static PreparedStatement insertEmployee;
    public static PreparedStatement insertMaintenance;
//    static PreparedStatement selectMaintenanceAll;

    public DatabaseConnect() {
        MYSQL_URL = "jdbc:mysql://localhost:3306";
        DB_URL = MYSQL_URL + "/SHMSDB";
        USERNAME = "root";
        PASSWORD = "12345678";
        try {
            //Connects to the SQL instance
            sqlConnection = DriverManager.getConnection(MYSQL_URL, USERNAME, PASSWORD);
            //Creates the database if not exists
            createSHMSDB = sqlConnection.prepareStatement("create database if not exists SHMSDB");
            createSHMSDB.executeUpdate();
            if (sqlConnection != null) {
                sqlConnection.close();
            }

            dbConnection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            st = dbConnection.createStatement();
            st.executeUpdate("drop table if exists management, building, employee, maintenance, maintenanceitem, room, student;");
            createTableBuilding = dbConnection.prepareStatement(""
                    + "create table if not exists building(\n"
                    + "buildingID int not null auto_increment,\n"
                    + "name varchar(50) not null,\n"
                    + "primary key (buildingID))");

            createTableRoom = dbConnection.prepareStatement(""
                    + "create table if not exists room(\n"
                    + "roomID int not null auto_increment,\n"
                    + "room VARCHAR(10) not null,\n"
                    + "buildingID int not null,\n"
                    + "primary key (roomID),\n"
                    + "foreign key (buildingID) REFERENCES building(buildingID))");

            createTableMaintenanceItem = dbConnection.prepareStatement(""
                    + "create table if not exists maintenanceItem(\n"
                    + "itemID int not null auto_increment,\n"
                    + "item varchar(50) not null,\n"
                    + "roomID int not null,\n"
                    + "primary key (itemID),\n"
                    + "foreign key (roomID) REFERENCES room(roomID))");

            createTableStudent = dbConnection.prepareStatement(""
                    + "create table if not exists student(\n"
                    + "studentID int not null auto_increment,\n"
                    + "userName varchar(50) not null,\n"
                    + "password varchar(50) not null,\n"
                    + "firstName varchar(50) not null,\n"
                    + "lastName varchar(50) not null,\n"
                    + "phone varchar(20) not null,\n"
                    + "emailID varchar(50),\n"
                    + "roomID int not null,\n"
                    + "userType varchar(15) not null,\n"
                    + "primary key (studentID),\n"
                    + "foreign key (roomID) REFERENCES room(roomID))");

            createTableManagement = dbConnection.prepareStatement(""
                    + "create table if not exists management(\n"
                    + "managementID int not null auto_increment,\n"
                    + "userName varchar(50) not null,\n"
                    + "password varchar(50) not null,\n"
                    + "firstName varchar(50) not null,\n"
                    + "lastName varchar(50) not null,\n"
                    + "phone varchar(20) not null,\n"
                    + "emailID varchar(50),\n"
                    + "userType varchar(15) not null,\n"
                    + "primary key (managementID))");

            createTableEmployee = dbConnection.prepareStatement(""
                    + "create table if not exists employee(\n"
                    + "employeeID int not null auto_increment,\n"
                    + "userName varchar(50) not null,\n"
                    + "password varchar(50) not null,\n"
                    + "firstName varchar(50) not null,\n"
                    + "lastName varchar(50) not null,\n"
                    + "phone varchar(20) not null,\n"
                    + "emailID varchar(50),\n"
                    + "userType varchar(15) not null,\n"
                    + "primary key (employeeID))");

            createTableMaintenance = dbConnection.prepareStatement(""
                    + "create table if not exists maintenance(\n"
                    + "maintenanceID int not null auto_increment,\n"
                    + "itemID int not null,\n"
                    + "description longtext not null,\n"
                    + "status ENUM('REQUESTED', 'UNDER_REVIEW', 'ASSIGNED', 'COMPLETED'),\n"
                    + "managementID int,\n"
                    + "studentID int not null,\n"
                    + "employeeID int,\n"
                    + "primary key (maintenanceID),\n"
                    + "foreign key (employeeID) REFERENCES employee(employeeID),\n"
                    + "foreign key (itemID) REFERENCES maintenanceItem(itemID),\n"
                    + "foreign key (studentID) REFERENCES student(studentID),\n"
                    + "foreign key (managementID) REFERENCES management(managementID))");

            createTableBuilding.executeUpdate();
            createTableRoom.executeUpdate();
            createTableMaintenanceItem.executeUpdate();
            createTableStudent.executeUpdate();
            createTableEmployee.executeUpdate();
            createTableManagement.executeUpdate();
            createTableMaintenance.executeUpdate();

            st.executeUpdate("INSERT INTO building " + "VALUES (1, 'Hopkins')");
            st.executeUpdate("INSERT INTO building " + "VALUES (2, 'McCall')");

            st.executeUpdate("INSERT INTO room " + "VALUES (1, 'H1', 1)");
            st.executeUpdate("INSERT INTO room " + "VALUES (2, 'H2', 1)");
            st.executeUpdate("INSERT INTO room " + "VALUES (3, 'M1', 2)");
            st.executeUpdate("INSERT INTO room " + "VALUES (4, 'M2', 2)");

            st.executeUpdate("INSERT INTO maintenanceItem " + "VALUES ('1', 'Air-Condition', 1)");
            st.executeUpdate("INSERT INTO maintenanceItem " + "VALUES ('2', 'Lamp', 2)");
            st.executeUpdate("INSERT INTO maintenanceItem " + "VALUES ('3', 'Power', 3)");
            st.executeUpdate("INSERT INTO maintenanceItem " + "VALUES ('4', 'Bed', 4)");

            st.executeUpdate("INSERT INTO student " + "VALUES ('1', 'tom1', 'tom1', 'Tom', 'Shark', '012345678', 'tom@gmail.com', 1, 'Student')");
            st.executeUpdate("INSERT INTO student " + "VALUES ('2', 'addylol', 'addylol', 'Addy', 'LOL', '054678978', 'addylol@gmail.com', 2, 'Student')");
            st.executeUpdate("INSERT INTO student " + "VALUES ('3', 'sarah11', 'sarah11', 'Sarah', 'Saeed', '078978944', 'sarah@gmail.com', 3, 'Student')");
            st.executeUpdate("INSERT INTO student " + "VALUES ('4', 'ellie22', 'ellie22', 'Ellie', 'Kendall', '012312323', 'ellie@live.com', 4, 'Student')");

            st.executeUpdate("INSERT INTO management " + "VALUES ('1', 'sunny1', 'sunny1', 'Sunny', 'Hawk', '012455823', 'sunny@live.com', 'Management')");
            st.executeUpdate("INSERT INTO management " + "VALUES ('2', 'jeff1', 'jeff1', 'Jeff', 'Bezos', '012665823', 'bezos@live.com', 'Management')");

            st.executeUpdate("INSERT INTO employee " + "VALUES ('1', 'georgia7', 'georgia7', 'Georgia', 'Canfell', '021245523', 'georgia@live.com', 'Employee')");
            st.executeUpdate("INSERT INTO employee " + "VALUES ('2', 'elon10', 'elon10','Elon', 'Musk', '012456643', 'elon@gmail.com', 'Employee')");

            st.executeUpdate("INSERT INTO maintenance " + "VALUES (1, 1, 'Does not cool', 'REQUESTED', 1, 1, 1)");
            st.executeUpdate("INSERT INTO maintenance " + "VALUES (2, 2, 'Bulb fused', 'UNDER_REVIEW', 2, 2, null)");
            st.executeUpdate("INSERT INTO maintenance " + "VALUES (3, 3, 'Power socket does not have power', 'ASSIGNED', 1, 3, 1)");
            st.executeUpdate("INSERT INTO maintenance " + "VALUES (4, 4, 'Noisy', 'COMPLETED', 2, 4, 2)");

//            insertBuilding = dbConnection.prepareStatement("insert into building"
//                    + "(name)"
//                    + "values (?)");
//            
//            insertRoom = dbConnection.prepareStatement("insert into room"
//                    + "(room, buildingID)"
//                    + "values (?, ?)");
//           
//            insertItem = dbConnection.prepareStatement("insert into maintenanceItem"
//                    + "(item, roomID)"
//                    + "values (?, ?)");
//            
//            insertStudent = dbConnection.prepareStatement("insert into student"
//                    + "(userName, firstName, lastName, phone, emailID, roomID, userType)"
//                    + "values (?,?,?,?,?,?,?)");
//            
//            insertEmployee = dbConnection.prepareStatement("insert into employee"
//                    + "(userName, firstName, lastName, phone, emailID, userType)"
//                    + "values (?,?,?,?,?,?)");
//            
//            insertManagement = dbConnection.prepareStatement("insert into management"
//                    + "(userName, firstName, lastName, phone, emailID, userType)"
//                    + "values (?,?,?,?,?,?)");
//
            insertMaintenance = dbConnection.prepareStatement("insert into maintenance"
                    + "(maintenanceID, itemID, description, status, managementID, studentID, employeeID)"
                    + "values (?,?,?,?,?,?,?)");
//            
//            selectMaintenanceAll = dbConnection.prepareStatement("select * from maintenance");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        }

    }

    public static Connection getDbConnection() {
        return dbConnection;
    }

}
