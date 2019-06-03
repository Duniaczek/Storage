package akademia.dbStorage.repositories;


import akademia.dbStorage.model.DbFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbFileRepository extends JpaRepository<DbFile, Long> {
    
}
