package csvParser;

import org.apache.commons.dbutils.DbUtils;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This is a utility class with all the database related methods.
 */
public final class DatabaseUtils {

    private DatabaseUtils(){
        //private empty constructor
    }

    /**
     * This is a minor helper method used in {@link #createDB(String)}.
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
     * Creates an initial table with 10 columns A-J after db connection.
     * @param dbName - A db name parsed from the user's csv file path.
     */
    public static void createDB(String dbName) {
        Connection conn = null;
        Statement stmt = null;

        //sql statement to create a table with 10 columns to match the csv.
        String sql = "CREATE TABLE records ("
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

        //if dbName isn't already a file
        if (!alreadyExists(dbName)) {

            //Check for driver
            try {
                Class.forName("org.sqlite.JDBC");
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            //connect to/create the db
            try{
                //creates new db on first connection attempt
                conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
                System.out.println("Database Connection Successful");

                //if the connection is established create the table
                if (conn != null) {
                    try {
                        stmt = conn.createStatement();
                        stmt.executeUpdate(sql);
                        System.out.println("Table created");
                    }
                    catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                    finally {
                        DbUtils.closeQuietly(stmt);
                        DbUtils.closeQuietly(conn);
                    }
                }
            }
            catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);
            }
        }
        else {
            System.out.println("Database File Already Exists");
        }
    }

    /**
     * This method connects to the database and batch inserts valid data from the csv.
     * @param goodRecords - a list of valid Records to be inserted.
     * @param dbName - the name of the database.
     * @param logger - logger object to log stats.
     */
    public static void batchInsert(List<Records> goodRecords, String dbName, Logger logger){
        Connection conn = null;

        //counters to track the batch
        int count = 0;
        int batch = 1000;

        //sql prepared statement to insert
        String sql = "INSERT INTO records (A, B, C, D, E, F, G, H, I, J) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            if (conn != null){

                //autocommit is off to avoid sync time, faster insert
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(sql);

                //iterate through all Records in the list and add them to the batch
                for (Records csvRow : goodRecords){
                    pstmt.setString(1, csvRow.getA());
                    pstmt.setString(2, csvRow.getB());
                    pstmt.setString(3, csvRow.getC());
                    pstmt.setString(4, csvRow.getD());
                    pstmt.setString(5, csvRow.getE());
                    pstmt.setString(6, csvRow.getF());
                    pstmt.setString(7, csvRow.getG());
                    pstmt.setString(8, csvRow.getH());
                    pstmt.setString(9, csvRow.getI());
                    pstmt.setString(10, csvRow.getJ());
                    pstmt.addBatch();

                    //if the batch size is at 1000, execute and commit the batch insert
                    if (++count % batch == 0){
                        pstmt.executeBatch();
                        conn.commit();
                    }
                }

                //insert remaining Records
                pstmt.executeBatch();
                conn.commit();
                DbUtils.closeQuietly(pstmt);
                DbUtils.closeQuietly(conn);

                logger.info("# of Records Successful: " + goodRecords.size());
            }
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
