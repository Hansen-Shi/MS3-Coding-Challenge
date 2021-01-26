package csvParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class runs the app and has some minor bits of setup.
 */
public class MainApp {
    public static void main(String[] args) {
        BufferedReader reader = null;

        //Get the filepath of the .csv file.
        String filePath = UserInputUtils.getUserInput();

        //Create a BufferedReader with the given filepath.
        try {
            reader = new BufferedReader(new FileReader(filePath));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Create the database and table.
        DatabaseUtils.createDB(UserInputUtils.dbNameParser(filePath));

        //Process the data from the csv
        CsvParserUtils.csvOrganizer(
                reader,
                UserInputUtils.csvNameParser(filePath),
                UserInputUtils.dbNameParser(filePath),
                UserInputUtils.logNameParser(filePath)
        );


    }
}
