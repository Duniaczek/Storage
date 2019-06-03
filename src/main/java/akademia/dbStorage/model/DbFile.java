package akademia.dbStorage.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity // reprezzentacja tabeli w bazie danych
@Table(name = "file")
public class DbFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    //@Column(name = "data")
    @Lob
    private byte[] data;
}
