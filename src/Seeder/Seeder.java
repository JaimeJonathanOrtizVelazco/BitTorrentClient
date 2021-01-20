package Seeder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Seeder extends Thread {
    private final String trackerIP;
    private ServerSocket server;

    public Seeder(String trackerIP) {
        this.trackerIP = trackerIP;
    }

    @Override
    public void run() {
        System.out.println("Enviando la lista de archivos disponibles");
        SocketConnection connection = new SocketConnection(trackerIP, 4201);
        SeederTorrents torrents = new SeederTorrents(trackerIP);
        connection.SendObject(torrents.getTorrents());
        try {
            server = new ServerSocket(4202);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        boolean loop = true;
        while (loop) {
            try {
                assert server != null;
                Socket socket = server.accept();
                new SeederClient(socket).start();
            } catch (Exception ex) {
                ex.printStackTrace();
                loop = false;
            }
        }
    }
}
