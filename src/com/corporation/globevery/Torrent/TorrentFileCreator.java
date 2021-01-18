package com.corporation.globevery.Torrent;

import java.io.*;
import java.util.Scanner;

public class TorrentFileCreator {
    private final String TrackerIP;
    private static final int PORT = 4203;

    public TorrentFileCreator(String TrackerIP) {
        this.TrackerIP = TrackerIP;
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
            dto.setTrackerIp(this.TrackerIP);
            dto.setTrackerPort(PORT);
            File torrent = new File("torrents/" + dto.getFileName() + ".torrent");
            torrent.createNewFile();
            FileOutputStream Fout = new FileOutputStream(torrent);
            ObjectOutputStream ObjectOut = new ObjectOutputStream(Fout);
            ObjectOut.writeObject(dto);
            ObjectOut.close();
            Fout.close();
        } else {
            System.out.println("Selecciona un nombre de archivo que este dentro de la lista");
        }
    }
}
