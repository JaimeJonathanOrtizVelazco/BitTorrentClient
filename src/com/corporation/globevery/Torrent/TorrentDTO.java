package com.corporation.globevery.Torrent;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class TorrentDTO implements Serializable {
    private static final int Piece_Size = 102400;
    private String TrackerIp;
    private int TrackerPort;
    private int Pieces;
    private int LastPiece;
    private String FileName;
    private String FileExtension;
    private String Id;
    private ArrayList<byte[]> CheckSum = new ArrayList<>();

    public TorrentDTO(String fileName) throws IOException {
        double FileSize = 0;
        int lastPiece = 0;
        Id = UUID.fromString(fileName).toString();
        String[] fileProp = fileName.split("\\.");
        FileName = fileProp[0];
        FileExtension = fileProp[1];
        String Path = "files/" + fileName;
        File file = new File(Path);
        FileSize = file.length();
        Pieces = (int) Math.ceil(FileSize / Piece_Size);
        if (Pieces * Piece_Size > FileSize) {
            LastPiece = (int) (FileSize - ((Pieces - 1) * Piece_Size));
        }
        InputStream InputStream = new FileInputStream(Path);
        byte[] data;
        for (int i = 0; i < Pieces; i++) {
            if (i < Pieces - 1) {
                data = new byte[Piece_Size];
                InputStream.read(data, 0, Piece_Size);
            } else {
                data = new byte[lastPiece];
                InputStream.read(data, 0, lastPiece);
            }
            CheckSum.add(data);
        }
        InputStream.close();
    }

    public void setTrackerIp(String trackerIp) {
        TrackerIp = trackerIp;
    }

    public void setTrackerPort(int trackerPort) {
        TrackerPort = trackerPort;
    }

    public String getFileName() {
        return FileName;
    }
}
