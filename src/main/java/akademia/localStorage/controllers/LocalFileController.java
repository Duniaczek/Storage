package akademia.localStorage.controllers;

import akademia.localStorage.model.LocalFile;
import akademia.localStorage.services.LocalFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static akademia.configs.Constans.*;

@RestController
public class LocalFileController {

    private LocalFileService localFileService;

    public LocalFileController(LocalFileService localFileService) {
        this.localFileService = localFileService;
    }

    @GetMapping(FILES)
    public List<LocalFile> getFiles() throws IOException {
        return localFileService.getFiles();
    }

    @PostMapping(UPLOAD_PATH)
    //@RequestMapping(method = RequestMethod.POST, value = FILES, produces = "applicaton/json")
    public ResponseEntity<LocalFile> uploudFile(@RequestParam("file")MultipartFile file) throws IOException {
        if(file.getSize() < 10_000_000L) {
            return new ResponseEntity<>(localFileService.uploadFile(file), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(DOWNLOAD_PATH + "{file}")
    public ResponseEntity<?> downloadFile(@PathVariable String file) throws IOException {

        Resource resource = localFileService.downloadFile(file);
        File targetFile = new File(resource.getFile().getAbsolutePath());
        String contentType = Files.probeContentType(targetFile.toPath());

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + targetFile.getName() + "\"")
                .contentLength(targetFile.length())
                .body(resource);
    }

    @DeleteMapping(DELETE_PATH + "{file}")
    public void deleteFile(@PathVariable String file) {
        localFileService.deleteFile(file);
    }
}
