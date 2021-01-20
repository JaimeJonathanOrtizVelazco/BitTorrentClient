package Torrent;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class TorrentDTO implements Serializable {
    private String trackerIp;
    private int trackerPort;
    private int pieces;
    private int lastPiece;
    private String fileName;
    private String fileExtension;
    private String id;
    private ArrayList<byte[]> checkSum = new ArrayList<>();

    public TorrentDTO(String fileName) throws IOException {
        int pieceSize = 102400;
        double FileSize = 0;
        int lastPiece = 0;
        id = UUID.randomUUID().toString();
        String[] fileProp = fileName.split("\\.");
        this.fileName = fileProp[0];
        fileExtension = fileProp[1];
        String Path = "files/" + fileName;
        File file = new File(Path);
        FileSize = file.length();
        pieces = (int) Math.ceil(FileSize / pieceSize);
        if (pieces * pieceSize > FileSize) {
            lastPiece = (int) (FileSize - ((pieces - 1) * pieceSize));
        }
        InputStream InputStream = new FileInputStream(Path);
        byte[] data;
        for (int i = 0; i < pieces; i++) {
            if (i < pieces - 1) {
                data = new byte[pieceSize];
                InputStream.read(data, 0, pieceSize);
            } else {
                data = new byte[lastPiece];
                InputStream.read(data, 0, lastPiece);
            }
            checkSum.add(data);
        }
        InputStream.close();
    }


    public String getTrackerIp() {
        return trackerIp;
    }

    public void setTrackerIp(String trackerIp) {
        this.trackerIp = trackerIp;
    }

    public int getTrackerPort() {
        return trackerPort;
    }

    public void setTrackerPort(int trackerPort) {
        this.trackerPort = trackerPort;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getLastPiece() {
        return lastPiece;
    }

    public void setLastPiece(int lastPiece) {
        this.lastPiece = lastPiece;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<byte[]> getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(ArrayList<byte[]> checkSum) {
        this.checkSum = checkSum;
    }
}
