package com.corporation.globevery;

import com.corporation.globevery.Peer.Peer;
import com.corporation.globevery.Peer.Seeder.Seeder;
import com.corporation.globevery.Torrent.TorrentFileCreator;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        int option = 0;
        int loop = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido");
        while (loop == 0) {
            System.out.println("Que desea realizar?");
            System.out.println("1) Descargar archivo desde torrent");
            System.out.println("2) Conectarse a una red");
            System.out.println("3) Terminar la aplicacion");
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1 -> {
                }
                case 2 -> {
                    System.out.println("Ingresa la IP del tracker al que te deseas conectar");
                    String trackerIp= scanner.nextLine();
                    Peer peer= new Peer(trackerIp);
                    peer.Connect();
                }
                case 3 -> loop = 1;
                default -> System.out.println("Ingresa una opcion correcta.");
            }
        }
    }
}
