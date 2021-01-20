package Seeder;

import java.io.Serializable;

public class SeederDTO implements Serializable {
    public String seederIP;
    public int port;

    public SeederDTO() {
    }

    public SeederDTO(String seederIP, int port) {
        this.seederIP = seederIP;
        this.port = port;
    }

    public String getSeederIP() {
        return seederIP;
    }

    public void setSeederIP(String seederIP) {
        this.seederIP = seederIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
