package com.corporation.globevery.Peer.Seeder;

import java.net.ServerSocket;
import java.net.Socket;

public class Seeder extends Thread{
    Socket[] Sockets;
    boolean loop=true;
    @Override
    public void run(){

        ServerSocket server = null;
        try {
            server = new ServerSocket(4200);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int i=0;
        while (loop) {
            try{
                assert server != null;
                Sockets[i]=server.accept();
                new SeederClient(Sockets[i]).start();
                i++;
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
    public void closeSockets(){

    }
}
