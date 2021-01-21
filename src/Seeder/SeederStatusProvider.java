package Seeder;

public class SeederStatusProvider extends Thread {
    private final String trackerIP;
    private final SocketConnection connection;

    public SeederStatusProvider(String trackerIP) {
        this.trackerIP = trackerIP;
        connection = new SocketConnection(trackerIP, 4201);
    }

    @Override
    public void run() {
        while (connection.Status()) {
            connection.ReceiveInputInt();
            SeederTorrents torrents = new SeederTorrents(trackerIP);
            connection.SendObject(torrents.getTorrents());
        }
        connection.close();
        System.out.println("Se ha perdido la conexion con el tracker");
    }

    public void end() {
        connection.close();
    }
}
