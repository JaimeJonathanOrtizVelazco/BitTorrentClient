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
        String fileName=connection.ReceiveInput();
        PrepareFile(fileName).forEach(p->{
            connection.SendObject(p);
        });
    }
    public ArrayList<byte[]> PrepareFile(String fileName){
        ArrayList<byte[]> fileArray= new ArrayList<>();
        int pieceSize = 102400;
        double FileSize=0;
        int pieces=0;
        int lastPiece=0;
        String Path = "files/" + fileName;
        File file = new File(Path);
        FileSize = file.length();
        pieces = (int) Math.ceil(FileSize / pieceSize);
        if (pieces * pieceSize > FileSize) {
            lastPiece = (int) (FileSize - ((pieces - 1) * pieceSize));
        }
        InputStream InputStream = null;
        try {
            InputStream = new FileInputStream(Path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] data;
        for (int i = 0; i < pieces; i++) {
            if (i < pieces - 1) {
                data = new byte[pieceSize];
                try {
                    InputStream.read(data, 0, pieceSize);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                data = new byte[lastPiece];
                try {
                    InputStream.read(data, 0, lastPiece);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fileArray.add(data);
        }
        try {
            InputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileArray;
    }
}
