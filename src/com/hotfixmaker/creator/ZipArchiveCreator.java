package com.hotfixmaker.creator;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

public class ZipArchiveCreator {

    public static void process(String archiveName, String targetFolder, File rootArchiveFolder) throws ZipException {
        ZipFile zipFile = new ZipFile(new File(targetFolder, archiveName + ".zip"));
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        zipFile.addFolder(rootArchiveFolder, parameters);
    }
}
