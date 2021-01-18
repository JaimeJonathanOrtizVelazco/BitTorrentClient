package com.corporation.globevery.Peer.Seeder;

import java.net.Socket;

public class SeederClient extends Thread{
    private Socket socket;
    public SeederClient(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run(){

    }
}
