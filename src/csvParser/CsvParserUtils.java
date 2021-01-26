package csvParser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * This is a utility class with all the methods to process data from a csv
 */
public final class CsvParserUtils {
    private CsvParserUtils(){
        //private empty constructor
    }

    /**
     * This method iterates through the csv row by row.
     * Records are separated into 2 separate lists based on their validity.
     * Calls {@link #writeToCsv(List, String, Logger)} to handle bad records.
     * Calls {@link DatabaseUtils#batchInsert(List, String, Logger)} to handle good records.
     * @param reader - a BufferedReader created beforehand.
     * @param csvName - the name of the bad csv file, parsed from the user's input.
     * @param dbName - the name of the database, parsed from the user's input.
     * @param logName - the name of the log file, parsed from the user's input.
     */
    public static void csvOrganizer(Reader reader, String csvName, String dbName, String logName){
        //create logger for stats post data organization
        Logger logger = LogStats.createLogger(logName);

        //creates a csv to bean reader to make use of the Records class
        CsvToBean<Records> rec = new CsvToBeanBuilder(reader).withType(Records.class).build();

        //lists for valid and invalid records
        List<Records> badRecords = new ArrayList<Records>();
        List<Records> goodRecords = new ArrayList<Records>();

        //iterate through the csv row by row
        for (Records csvRow : rec){

            //add the row as a Records object to it's respective list
            if (csvRow.validRecord()) {
                goodRecords.add(csvRow);
            }
            else{
                badRecords.add(csvRow);
            }
        }

        logger.info("# of Records Received: " + (badRecords.size() + goodRecords.size()));

        //write the bad records to a csv file
        writeToCsv(badRecords, csvName, logger);

        //insert the good records into the database
        DatabaseUtils.batchInsert(goodRecords, dbName, logger);

    }

    /**
     * This Method writes all of the Records from a list into a csv file.
     * @param badRecords - a list of rejected Records.
     * @param csvName - the name of the bad csv file.
     * @param logger - a logger object to log stats.
     */
    public static void writeToCsv(List<Records> badRecords, String csvName, Logger logger){
        try {
            //create new file to write to
            Writer writer = new FileWriter(csvName);

            //writer for writing from Records to the csv
            StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();

            //write all Records from the list into the csv
            beanToCsv.write(badRecords);
            writer.close();
        }
        catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }

        logger.info("# of Records Failed: " + badRecords.size());
    }
}
