package csvParser;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This is a class with methods used to create Loggers
 */
public final class LogStats {
    private LogStats(){
        //private empty constructor
    }

    /**
     * This Method creates a logger then writes to a file with logName.
     * @param logName - the name of the file where logs are written to.
     * @return - the configured logger.
     */
    public static Logger createLogger(String logName){

        Logger logger = Logger.getLogger("statsLog");
        FileHandler fh;

        try{
            //logs write to a file with logName
            fh = new FileHandler(logName, true);
            logger.addHandler(fh);

            SimpleFormatter format = new SimpleFormatter();
            fh.setFormatter(format);

            //logs don't show in console
            logger.setUseParentHandlers(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}
