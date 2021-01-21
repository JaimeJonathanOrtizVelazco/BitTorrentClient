package Leecher;

import Seeder.SocketConnection;

public class GetPieces extends Thread {
    private final SocketConnection connection;
    private final int piece;

    public GetPieces(SocketConnection connection, int piece) {
        this.connection = connection;
        this.piece = piece;
    }

    @Override
    public void run() {
        connection.SendInputInt(piece);
        Leecher.file.set(piece, (byte[]) connection.ReceiveObject());
        Leecher.DownloadedPieces++;
        double percent = (double) (Leecher.DownloadedPieces * 100) / Leecher.TotalPieces;
        System.out.printf("\rDescargado: %.2f %%", percent);
        connection.Working = false;
    }
}
