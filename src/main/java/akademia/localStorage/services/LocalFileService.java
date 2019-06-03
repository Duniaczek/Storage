package akademia.localStorage.services;

import akademia.localStorage.model.LocalFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static akademia.configs.Constans.*;

@Service
public class LocalFileService {

    private static Logger logger = Logger.getLogger(LocalFileService.class.getName());

    public LocalFileService() {
        creatDirectory();
    }

    public void creatDirectory() {

        Path path = Paths.get(LOCAL_FILE_PATH);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.log(Level.INFO, path.toUri().toString());
            } catch (IOException e) {
                e.printStackTrace();
                logger.log(Level.WARNING, "Cannot creat directory: " + LOCAL_FILE_PATH);
            }
        } else {
            logger.log(Level.INFO, "Directory: " + LOCAL_FILE_PATH + " already exist! ");
        }
    }

    public List<LocalFile> getFiles() throws IOException {
        return Files
                .walk(Paths.get(LOCAL_FILE_PATH))
                .filter(Files::isRegularFile)
                .map(f -> {

                    try {
                        BasicFileAttributes bs = Files.readAttributes(f, BasicFileAttributes.class);
                        String fileDownloadUri = ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path(DOWNLOAD_PATH)
                                .path(f.getFileName().toString())
                                .toUriString();

                        String fileDeleteUri = ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path(DELETE_PATH)
                                .path(f.getFileName().toString())
                                .toUriString();

                        return new LocalFile.Builder()
                                .name(f.getFileName().toString())
                                .creationTime(bs.creationTime().toString())
                                .size(bs.size())
                                .lastModified(bs.lastModifiedTime().toString())
                                .downloadURI(fileDownloadUri)
                                .deleteURI(fileDeleteUri)
                                .fileType(Files.probeContentType(f.toAbsolutePath()))
                                .build();

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    public LocalFile uploadFile(MultipartFile file) throws IOException {
        Path path = Paths.get(LOCAL_FILE_PATH + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return new LocalFile.Builder()
                .name(file.getName())
                .build();
    }

    public Resource downloadFile(String file) throws MalformedURLException {

        Path path = Paths.get(LOCAL_FILE_PATH + file);
        Resource resource = new UrlResource(path.toUri());

        return resource;
    }

    public void deleteFile(String fileName) {

        File file = new File(LOCAL_FILE_PATH + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
