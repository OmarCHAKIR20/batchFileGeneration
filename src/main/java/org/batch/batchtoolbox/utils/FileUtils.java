package org.batch.batchtoolbox.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public boolean ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                log.error("Failed to create directory: {}", parentDir.getAbsolutePath());
                return false;
            }
        }
        return true;
    }

    public long countLinesInFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.lines(path).count();
        } catch (IOException e) {
            log.error("Error counting lines in file: {}", e.getMessage());
            return -1;
        }
    }
}
