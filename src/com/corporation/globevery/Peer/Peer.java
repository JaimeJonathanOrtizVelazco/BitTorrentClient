package com.corporation.globevery.Peer;

import com.corporation.globevery.Peer.Seeder.Seeder;
import com.corporation.globevery.Torrent.TorrentFileCreator;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Peer {
    private String trackerIP;
/*    private DataOutputStream DataOutput;
    private DataInputStream DataInput;
    private ObjectOutputStream ObjectOutput;
    private ObjectInputStream ObjectInput;*/

    public Peer(String TrackerIP) {
        trackerIP = TrackerIP;
/*        try {
            DataOutput = new DataOutputStream(socket.getOutputStream());
            DataInput = new DataInputStream(socket.getInputStream());
            ObjectOutput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInput = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) {
            System.out.println(ex);
        }*/
    }

    public void Connect() throws IOException {
        boolean loop = true;
//        boolean seederSign=false;
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
                    Seeder seeder = new Seeder();
                    seeder.start();
//                    seederSign=true;
                }
                case 3 -> {
                    loop = false;
                }
            }
        }
    }
}
