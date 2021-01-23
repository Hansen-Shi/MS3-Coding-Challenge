package csvParser;

import java.io.File;
import java.sql.*;

/**
 * This class contains all the database related methods
 * All methods are static as there is no real reason to instantiate this class
 * Handles xyz...
 */
public class databaseUtils {
    /**
     * This is a minor helper method used in the {@link #createDB(String)} method.
     * It checks if a file already exists with the user entered name.
     *
     * @param dbName - A proposed name for a new database file given by the user.
     * @return - returns true if the file already exists, returns false if nonexistent.
     */
    public static boolean alreadyExists(String dbName) {
        File temp = new File(dbName);
        return temp.exists();
    }

    public static void createDB(String dbName) {
        dbName = dbName + ".db";
        if (!alreadyExists(dbName)) {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
                if (c != null) {
                    System.out.println("Created Database Successfully");
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        } else {
            System.out.println("Database File Already Exists");
        }
    }

}
