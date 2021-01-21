package Leecher;

import Seeder.SeederDTO;
import Seeder.SocketConnection;
import Torrent.TorrentDTO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Leecher {
    public static ArrayList<SeederDTO> seeders = new ArrayList<>();
    public static ArrayList<SocketConnection> connections = new ArrayList<>();
    public static ArrayList<byte[]> file = new ArrayList<>();

    public Leecher() {
        seeders.clear();
        connections.clear();
        file.clear();
    }

    public void Connect() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String fileName;
        File directory = new File("torrents/");
        String[] files = directory.list();
        System.out.println("Que archivo desea utilizar?");
        if (files == null) {
            System.out.println("No hay archivos en el directorio");
        } else {
            for (String file : files) {
                System.out.println(file);
            }
        }
        fileName = scanner.nextLine();
        File fileSelected = new File("torrents/" + fileName);
        if (fileSelected.exists()) {
            TorrentDTO object = GetFileObject(fileName);
            for (int i=0;i<object.getPieces();i++){
                file.add(new byte[102400]);
            }
            String finalFileName = object.getFileName() + "." + object.getFileExtension();
            new SeedersList(object.getTrackerIp(), object.getTrackerPort(), object.getId());
            connections.forEach(c -> {
                c.SendInput(finalFileName);
            });
            //Aqui me quede para iniciar
            int i = 0;
            GetPieces piece;
            while (i < object.getPieces()) {
                Optional<SocketConnection> connection = connections.stream().filter(x -> !x.Working).findFirst();
                if (connection.isPresent()) {
                    connection.get().Working=true;
                    piece = new GetPieces(connection.get(), i);
                    piece.start();
                    i++;
                }
            }
            //Aqui termina
            connections.forEach(SocketConnection::close);
            GenerateFile(file, finalFileName);
            System.out.println("El archivo se descargo y creo");
        } else {
            System.out.println("Selecciona un nombre de archivo que este dentro de la lista");
        }
    }

    public TorrentDTO GetFileObject(String fileName) {
        TorrentDTO object = null;
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream("torrents/" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream objectIn = null;
        try {
            objectIn = new ObjectInputStream(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert objectIn != null;
            object = (TorrentDTO) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            objectIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    public void GenerateFile(ArrayList<byte[]> file, String fileName) throws IOException {
        OutputStream objectOut = new FileOutputStream("downloads/" + fileName);
        file.forEach(f -> {
            try {
                objectOut.write(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
