package csvParser;

import java.io.File;
import java.util.Scanner;

/**
 * This is a utility class that has methods dealing with modifying user input.
 */
public final class UserInputUtils {
    private UserInputUtils(){
        //private empty constructor
    }

    /**
     * This method gets a file path from the user via scanner.
     * @return - returns a String of the user's file path with quotations trimmed.
     */
    public static String getUserInput(){
        String userFile;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter File Path");
        userFile = scanner.nextLine();
        scanner.close();

        //trim quotations if the user shift + right-clicked 'save as path'.
        userFile = userFile.replaceAll("^\"|\"$", "");

        return userFile;
    }

    /**
     * This method takes the user's file path and trims it to a suitable database name.
     * @param filePath - the user's file path to their csv.
     * @return - the name of the database to be created.
     */
    public static String dbNameParser(String filePath){
        File file = new File(filePath);
        String name = file.getName();
        name = name.split("\\.")[0];
        return name + ".db";
    }

    /**
     * This method takes the user's file path and trims it to a suitable csv file name.
     * @param filePath - the user's file path.
     * @return - the name of the eventual bad csv file.
     */
    public static String csvNameParser(String filePath){
        File file = new File(filePath);
        String name = file.getName();
        name = name.split("\\.")[0];
        return name + "-bad.csv";
    }

    /**
     * This method takes the user's file path and trims it to a suitable log file name.
     * @param filePath - the user's file path.
     * @return - the name of the eventual log file.
     */
    public static String logNameParser(String filePath){
        File file = new File(filePath);
        String name = file.getName();
        name = name.split("\\.")[0];
        return name + ".log";
    }
}
