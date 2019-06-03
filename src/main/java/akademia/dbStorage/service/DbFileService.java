package akademia.dbStorage.service;

import akademia.dbStorage.model.DbFile;
import akademia.dbStorage.repositories.DbFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbFileService {

    private DbFileRepository dbFileRepository;

    public DbFileService(DbFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    public List<DbFile> getFiles() {
        return dbFileRepository.findAll();
    }
}
