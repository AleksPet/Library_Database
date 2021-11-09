package com.cydeo.utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB_Util {

    // declaring at class level so all methods can access
    private static Connection con;
    private static Statement stm;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;


    public static void createConnection(String url, String username, String password) {
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("CONNECTION SUCCESSFUL");
        } catch (Exception e) {
            System.out.println("CONNECTION HAS FAILED " + e.getMessage());
        }
    }


    /**
     * Create connection method , just checking one connection successful or not
     */
    public static void createConnection() {

        String url = ConfigReader.read("hr.database.url");
        String username = ConfigReader.read("hr.database.username");
        String password = ConfigReader.read("hr.database.password");
//        try {
//            con = DriverManager.getConnection(url , username, password) ;
//            System.out.println("CONNECTION SUCCESSFUL");
//        } catch (Exception e) {
//            System.out.println("CONNECTION HAS FAILED " + e.getMessage() );
//        }
        createConnection(url, username, password);

    }


    public static void destroy() {
        // WE HAVE TO CHECK IF WE HAVE THE VALID OBJECT FIRST BEFORE CLOSING THE RESOURCE
        // BECAUSE WE CAN NOT TAKE ACTION ON AN OBJECT THAT DOES NOT EXIST
        try {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE CLOSING RESOURCES " + e.getMessage());
        }

    }

    public static ResultSet runQuery(String sql) {

        try {
            stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql); // setting the value of ResultSet object
            rsmd = rs.getMetaData();  // setting the value of ResultSetMetaData for reuse
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE RUNNING QUERY " + e.getMessage());
        }

        return rs;

    }

    public static List<String> getColumnDataAsList(int columnNum) {

        List<String> columnDataLst = new ArrayList<>();

        try {
            rs.beforeFirst(); // make sure the cursor is at before first location
            while (rs.next()) {

                String cellValue = rs.getString(columnNum);
                columnDataLst.add(cellValue);
            }

        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE getColumnDataAsList " + e.getMessage());
        } finally {
            resetCursor();
        }
        return columnDataLst;
    }

    private static void resetCursor(){

        try {
            rs.beforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}





