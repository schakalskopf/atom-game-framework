/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
@author atomix
 */
public class TestHSQLBD {

    static String database = "/path/to/your/app/demobase";
    static String createSql = "CREATE TABLE Bookmarks (title VARCHAR(50), url VARCHAR(255));"
            + "INSERT INTO Bookmarks (title, url) VALUES ('Java Technology', 'String1');"
            + "INSERT INTO Bookmarks (title, url) VALUES ('HSQLDB 100% Java Database', 'String1');"
            + "INSERT INTO Bookmarks (title, url) VALUES ('Apache Jakarta Tomcat', 'String1');";
    static String selectSql = "SELECT title, url FROM Bookmarks ORDER BY title";
    static Connection connection;

    public static void main(String args[]) {
        try {

            Class.forName("org.hsqldb.jdbcDriver");


            connection = DriverManager.getConnection("jdbc:hsqldb:mem:testDb", "SA", "");

            System.out.println("TestDemobase\n");

            Statement statement = null;
            ResultSet resultSet = null;


            statement = connection.createStatement();
            statement.executeQuery(createSql);
            resultSet = statement.executeQuery(selectSql);

            while (resultSet.next()) {
                System.out.println(resultSet.getString("title") + " ("
                        + resultSet.getString("url") + ")");

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TestHSQLBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return;

        }
    }
}
