package Leecher;

import Seeder.SeederDTO;
import Seeder.SocketConnection;

import java.util.ArrayList;

public class SeedersList {
    public SeedersList(String trackerIp, int trackerPort, String Id) {
        SocketConnection connection = new SocketConnection(trackerIp, trackerPort);
        connection.ReceiveInputInt();
        connection.SendInput(Id);
        Leecher.seeders = (ArrayList<SeederDTO>) connection.ReceiveObject();
        Leecher.seeders.forEach(seed -> {
            SocketConnection con = new SocketConnection(seed.seederIP, seed.port);
            Leecher.connections.add(con);
        });
        connection.close();
    }

}
