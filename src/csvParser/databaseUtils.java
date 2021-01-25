package csvParser;

import java.io.File;
import java.sql.*;

/**
 * This is a utility class with all the database related methods.
 */
public final class databaseUtils {

    private databaseUtils(){
        //private constructor
    }

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

    /**
     * This method attempts to connect to/create a new database.
     * Calls {@link #createTable(String, String)} method to create an initial table.
     * @param dbName - A db name parsed from the user's csv file path.
     */
    public static void createDB(String dbName) {
        Connection conn = null;

        //sqlite statement to create a table with 10 columns to match the csv.
        String statement = "CREATE TABLE records ("
                + "A TEXT NOT NULL,"
                + "B TEXT NOT NULL,"
                + "C TEXT NOT NULL,"
                + "D TEXT NOT NULL,"
                + "E TEXT NOT NULL,"
                + "F TEXT NOT NULL,"
                + "G TEXT NOT NULL,"
                + "H TEXT NOT NULL,"
                + "I TEXT NOT NULL,"
                + "J TEXT NOT NULL)";

        //if dbName isn't already a file connect to it/create the db
        if (!alreadyExists(dbName)) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
                System.out.println("Database Connection Successful");

                //if the connection is established create the table
                if (conn != null) {
                    createTable(dbName, statement);
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        else {
            System.out.println("Database File Already Exists");
        }
    }

    /**
     * This method is used to create new tables for a database
     * @param dbName - the name of the database
     * @param statement - a formatted sqlite statement to be executed
     */
    public static void createTable(String dbName, String statement){

        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            if (conn != null) {
                try {
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(statement);
                    System.out.println("Table created");
                }
                catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void insertRecord(){

    }
}
