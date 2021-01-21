package Seeder;

import java.io.Serializable;
import java.util.ArrayList;

public class SeederTorrentDTO implements Serializable {
    public String id;
    public ArrayList<SeederDTO> seeders = new ArrayList<>();

    public SeederTorrentDTO() {
    }

    public SeederTorrentDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<SeederDTO> getSeeders() {
        return seeders;
    }

    public void setSeeders(ArrayList<SeederDTO> seeders) {
        this.seeders = seeders;
    }
}
