package akademia.localStorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalFile {
    private String name;
    private String creationTime;
    private String lastModified;
    private Long size;
    private String deleteURI;
    private String downloadURI;
    private String fileType;

    private LocalFile(Builder b) {

        this.name = b.name;
        this.creationTime = b.creationTime;
        this.lastModified = b.lastModified;
        this.size = b.size;
        this.deleteURI = b.deleteURI;
        this.downloadURI = b.downloadURI;
        this.fileType = b.fileType;
    }

    public static class Builder {
        private String name;
        private String creationTime;
        private String lastModified;
        private Long size;
        private String deleteURI;
        private String downloadURI;
        private String fileType;

        public Builder() {

        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder creationTime(String creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder lastModified(String lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder size(Long size) {
            this.size = size;
            return this;
        }

        public Builder deleteURI(String deleteURI) {
            this.deleteURI = deleteURI;
            return this;
        }

        public Builder downloadURI(String downloadURI) {
            this.downloadURI = downloadURI;
            return this;
        }

        public Builder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public LocalFile build() {
            return new LocalFile(this);
        }
    }
}
