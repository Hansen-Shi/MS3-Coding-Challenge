package csvParser;

import java.io.File;
import java.util.Scanner;

public class mainApp {

    public static String getUserInput(){
        String userFile;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter File Path");
        userFile = scanner.nextLine();
        return userFile;
    }

    public static String dbNameParser(String filePath){
        File file = new File(filePath);
        String name = file.getName();
        name = name.split("\\.")[0];
        return name + ".db";
    }

    public static void main(String[] args) {
        //String dbName = dbNameParser(dbNameParser("\"C:\\Users\\CodingBoi\\Downloads\\coding challenge\\ms3Interview.csv\""));
        //databaseUtils.createDB(dbName);
    }
}
