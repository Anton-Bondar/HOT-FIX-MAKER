package com.hotfixmaker.helper;

import com.hotfixmaker.model.SelectedFile;
import javafx.collections.ObservableList;
import org.apache.log4j.*;

import java.io.File;
import java.io.IOException;

public class LoggerHelper {

    public static void initLogger(File tmpFolder, Logger logger) throws IOException {
        SimpleLayout layout = new SimpleLayout();
        File logFile = new File(tmpFolder, "/logs/hfm_process.log");
        RollingFileAppender appender = new RollingFileAppender(layout, logFile.getPath());
        appender.activateOptions();
        appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        appender.setThreshold(Level.DEBUG);
        logger.setLevel(Level.DEBUG);
        logger.getRootLogger().addAppender(appender);
    }

    public static void logInputParams(String archiveName, String targetFolderPath, String defaultFolderPath,
                                      ObservableList<SelectedFile> filesForPacking, Logger logger) {
        logger.info("------------------");
        logger.info("name: " + archiveName);
        logger.info("target folder: " + targetFolderPath);
        logger.info("default folder: " + defaultFolderPath);
        logger.info("Files for packing: ");
        for (SelectedFile selectedFile : filesForPacking) {
            logger.info(selectedFile.getPath());
        }
        logger.info("------------------");
    }
}
