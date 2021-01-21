import Seeder.Seeder;
import Torrent.TorrentFileCreator;

import java.io.*;
import java.util.Scanner;

public class SeederMenu {
    private final String trackerIP;
    private static boolean seederStatus;
    private static Seeder seeder;

    public SeederMenu(String TrackerIP) {
        trackerIP = TrackerIP;
    }

    public void Connect() throws IOException {
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("Que desea realizar?");
            System.out.println("1) Generar archivo torrent para compartir");
            if (!seederStatus)
                System.out.println("2) Conectarse como proveedor");
            else
                System.out.println("2) Desconectarse como proveedor");
            System.out.println("3) Menu anterior");
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1 -> {
                    TorrentFileCreator creator = new TorrentFileCreator(trackerIP);
                    creator.CreateTorrent();
                    System.out.println("Archivo torrent generado");
                }
                case 2 -> {
                    if (!seederStatus) {
                        seeder = new Seeder(trackerIP);
                        seeder.start();
                        seederStatus = true;
                    } else {
                        seeder.end();
                        seederStatus = false;
                    }
                }
                case 3 -> {
                    loop = false;
                }
            }
        }
    }
}
