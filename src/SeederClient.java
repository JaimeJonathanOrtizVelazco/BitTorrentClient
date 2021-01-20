import Seeder.Seeder;
import Torrent.TorrentFileCreator;

import java.io.*;
import java.util.Scanner;

public class SeederClient {
    private final String trackerIP;

    public SeederClient(String TrackerIP) {
        trackerIP = TrackerIP;
    }

    public void Connect() throws IOException {
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("Que desea realizar?");
            System.out.println("1) Generar archivo torrent para compartir");
            System.out.println("2) Conectarse como proveedor");
            System.out.println("3) Salir");
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1 -> {
                    TorrentFileCreator creator = new TorrentFileCreator(trackerIP);
                    creator.CreateTorrent();
                    System.out.println("Archivo torrent generado");
                }
                case 2 -> {
                    Seeder seeder = new Seeder(trackerIP);
                    seeder.start();
                }
                case 3 -> {
                    loop = false;
                }
            }
        }
    }
}
