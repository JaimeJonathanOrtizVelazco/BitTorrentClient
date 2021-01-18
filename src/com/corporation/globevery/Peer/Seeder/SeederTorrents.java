package com.corporation.globevery.Peer.Seeder;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SeederTorrents implements Serializable {
    private String IPAddress;
    private int PORT;
    private ArrayList<String> Torrents;

    public SeederTorrents() throws UnknownHostException {
        this.IPAddress = InetAddress.getLocalHost().getHostAddress();
        this.PORT = 4200;
        this.Torrents = new ArrayList<>();
        
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public int getPORT() {
        return PORT;
    }

    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    public ArrayList<String> getTorrents() {
        return Torrents;
    }

    public void setTorrents(ArrayList<String> torrents) {
        Torrents = torrents;
    }
}
