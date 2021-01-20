package Leecher;

import Seeder.SeederDTO;
import Seeder.SocketConnection;
import Torrent.TorrentDTO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Leecher {
    public void Connect() throws IOException {
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
            fileName = object.getFileName() + "." + object.getFileExtension();
            ArrayList<SocketConnection> connections = new ArrayList<>();
            GetSeedersList(object).forEach(s -> {
                SocketConnection socketConnection = new SocketConnection(s.seederIP, s.port);
                connections.add(socketConnection);
            });
            connections.get(0).SendInput(fileName);
            ArrayList<byte[]> file = new ArrayList<>();
            for (int i = 0; i < object.getPieces(); i++) {
                file.add((byte[]) connections.get(0).ReceiveObject());
            }
            connections.get(0).close();
            GenerateFile(file, fileName);
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

    public ArrayList<SeederDTO> GetSeedersList(TorrentDTO object) {
        ArrayList<SeederDTO> seederList = new ArrayList<>();
        SocketConnection connection = new SocketConnection(object.getTrackerIp(), object.getTrackerPort());
        connection.SendInput(object.getId());
        seederList = (ArrayList<SeederDTO>) connection.ReceiveObject();
        return seederList;
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
