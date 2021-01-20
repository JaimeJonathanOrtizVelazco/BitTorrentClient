package Seeder;

import Torrent.TorrentDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SeederTorrents {
    ArrayList<SeederTorrentDTO> Torrents = new ArrayList<>();

    public SeederTorrents(String trackerIP) {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert address != null;
        SeederDTO seederDTO = new SeederDTO(address.getHostAddress(), 4202);
        File directory = new File("torrents/");
        String[] files = directory.list();
        if (files == null) {
            System.out.println("No hay archivos que adjuntar");
        } else {
            for (String file : files) {
                try {
                    FileInputStream fileIn = new FileInputStream("torrents/" + file);
                    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                    TorrentDTO torrentObject = (TorrentDTO) objectIn.readObject();
                    objectIn.close();
                    if (torrentObject.getTrackerIp().equals(trackerIP)) {
                        SeederTorrentDTO seeder = new SeederTorrentDTO(torrentObject.getId(), seederDTO);
                        this.Torrents.add(seeder);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public ArrayList<SeederTorrentDTO> getTorrents() {
        return Torrents;
    }
}
