package Torrent;

import java.io.*;
import java.util.Scanner;

public class TorrentFileCreator {
    private final String trackerIP;
    private static final int port = 4200;

    public TorrentFileCreator(String TrackerIP) {
        this.trackerIP = TrackerIP;
    }

    public void CreateTorrent() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        File directory = new File("files/");
        String[] files = directory.list();
        System.out.println("Que archivo desea utilizar?");
        if (files == null) {
            System.out.println("No hay archivos en el directorio");
        } else {
            for (String file : files) {
                System.out.println(file);
            }
        }
        fileName = scanner.nextLine();
        File fileSelected = new File("files/" + fileName);
        if (fileSelected.exists()) {
            TorrentDTO dto = new TorrentDTO(fileName);
            System.out.println("Identificador:" + dto.getId());
            dto.setTrackerIp(this.trackerIP);
            dto.setTrackerPort(port);
            File torrent = new File("torrents/" + dto.getFileName() + ".torrent");
            FileOutputStream fileOut = new FileOutputStream(torrent);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(dto);
            objectOut.close();
        } else {
            System.out.println("Selecciona un nombre de archivo que este dentro de la lista");
        }
    }
}
