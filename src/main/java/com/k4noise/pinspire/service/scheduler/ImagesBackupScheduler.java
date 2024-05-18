package com.k4noise.pinspire.service.scheduler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ImagesBackupScheduler  {
    String backupDirPath;
    String imagesDirPath;

    public ImagesBackupScheduler(
            @Value("${app.backup.dir}") String backupDirPath,
            @Value("${app.images.dir}") String imagesDirPath) {
        this.backupDirPath = backupDirPath;
        this.imagesDirPath = imagesDirPath;
    }

    @Scheduled(cron = "${app.backup.job.cron}")
    public void execute() {

        try {
            Files.createDirectories(Paths.get(backupDirPath));
            Files.createDirectories(Paths.get(imagesDirPath));
            log.info("Started images backup");
            zipFolder(Paths.get(imagesDirPath), Paths.get(backupDirPath));
            log.info("Finished images backup");
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }

    private static void zipFolder(Path sourceFolderPath, Path zipFilePath) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath.toFile()));

        Files.walk(sourceFolderPath)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    ZipEntry zipEntry = new ZipEntry(sourceFolderPath.relativize(path).toString());
                    try {
                        zipOutputStream.putNextEntry(zipEntry);
                        Files.copy(path, zipOutputStream);
                        zipOutputStream.closeEntry();
                    } catch (IOException exception) {
                        log.error(exception.getMessage());
                    }
                });

        zipOutputStream.close();
    }
}
