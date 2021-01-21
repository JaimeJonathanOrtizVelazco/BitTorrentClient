package Seeder;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SeederClient extends Thread {
    SocketConnection connection;

    public SeederClient(Socket socket) {
        connection = new SocketConnection(socket);
    }

    @Override
    public void run() {
        while (connection.Status() && !Seeder.endMut) {
            String fileName = connection.ReceiveInput();
            ArrayList<byte[]> fileInBytes = null;
            try {
                fileInBytes = PrepareFile(fileName);
            } catch (IOException e) {
                System.out.println("Error al recuperar el archivo");
                break;
            }
            int piece = 0;
            while (connection.Status() && !Seeder.endMut) {
                piece = connection.ReceiveInputInt();
                connection.SendObject(fileInBytes.get(piece));
            }
        }
        connection.close();
    }

    public ArrayList<byte[]> PrepareFile(String fileName) throws IOException {
        ArrayList<byte[]> fileArray = new ArrayList<>();
        int pieceSize = 102400;
        double FileSize = 0;
        int pieces = 0;
        int lastPiece = 0;
        String Path = "files/" + fileName;
        File file = new File(Path);
        FileSize = file.length();
        pieces = (int) Math.ceil(FileSize / pieceSize);
        if (pieces * pieceSize > FileSize) {
            lastPiece = (int) (FileSize - ((pieces - 1) * pieceSize));
        }
        InputStream InputStream = null;
        InputStream = new FileInputStream(Path);
        byte[] data;
        for (int i = 0; i < pieces; i++) {
            if (i < pieces - 1) {
                data = new byte[pieceSize];
                InputStream.read(data, 0, pieceSize);
            } else {
                data = new byte[lastPiece];
                InputStream.read(data, 0, lastPiece);
            }
            fileArray.add(data);
        }
        InputStream.close();
        return fileArray;
    }
}
