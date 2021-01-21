package Seeder;

import java.io.*;
import java.net.Socket;

public class SocketConnection {
    private DataOutputStream DataOutput;
    private DataInputStream DataInput;
    private ObjectOutputStream ObjectOutput;
    private ObjectInputStream ObjectInput;
    private Socket socket;
    public boolean Working = false;

    public SocketConnection(Socket socket) {
        this.socket = socket;
        if (socket != null)
            Init();
    }

    public SocketConnection(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
        } catch (IOException ignored) {
            System.out.println("No se pudo establecer conexion con el servidor");
        }
        if (socket != null)
            Init();
    }

    private void Init() {
        try {
            this.DataOutput = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {
            close();
        }
        try {
            this.DataInput = new DataInputStream(socket.getInputStream());
        } catch (IOException ignored) {
            close();
        }
        try {
            this.ObjectOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ignored) {
            close();
        }
        try {
            this.ObjectInput = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ignored) {
            close();
        }
    }

    public boolean Status() {
        if (this.socket.isClosed()) {
            return false;
        } else {
            return this.socket.isConnected();
        }
    }

    public String getIp() {
        return this.socket.getInetAddress().toString();
    }

    public <T> void SendObject(T Object) {
        try {
            this.ObjectOutput.writeObject(Object);
        } catch (IOException ignored) {
            close();
        }
    }

    public void SendInput(String value) {
        try {
            this.DataOutput.writeUTF(value);
        } catch (IOException ignored) {
            close();
        }
    }

    public void SendInputInt(int value) {
        try {
            this.DataOutput.writeInt(value);
        } catch (IOException ignored) {
            close();
        }
    }

    public Object ReceiveObject() {
        try {
            return this.ObjectInput.readObject();
        } catch (IOException | ClassNotFoundException ignored) {
            close();
            return null;
        }
    }

    public String ReceiveInput() {
        try {
            return this.DataInput.readUTF();
        } catch (IOException ignored) {
            close();
            return null;
        }
    }

    public int ReceiveInputInt() {
        try {
            return this.DataInput.readInt();
        } catch (IOException ignored) {
            close();
            return 0;
        }
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException ignored) {
        }
    }
}
