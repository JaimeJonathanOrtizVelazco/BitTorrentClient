package Seeder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Seeder extends Thread {
    private final ServerSocket server;
    private final SeederStatusProvider provider;
    public static boolean endMut;
    public Seeder(String trackerIP) throws IOException {
        endMut=false;
        provider = new SeederStatusProvider(trackerIP);
        server = new ServerSocket(4202);
    }

    @Override
    public void run() {
        provider.start();
        while (true) {
            try {
                Socket socket = server.accept();
                new SeederClient(socket).start();
            } catch (Exception ex) {
                break;
            }
        }
    }

    public void end() throws IOException {
        endMut=true;
        provider.end();
        server.close();
    }
}
