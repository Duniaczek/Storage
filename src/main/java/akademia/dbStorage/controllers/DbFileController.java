package akademia.dbStorage.controllers;

import akademia.dbStorage.model.DbFile;
import akademia.dbStorage.service.DbFileService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DbFileController {

    private DbFileService dbFileService;

    public DbFileController(DbFileService dbFileService) {
        this.dbFileService = dbFileService;
    }

    public List<DbFile> getFiles() {
        return dbFileService.getFiles();
    }
}
