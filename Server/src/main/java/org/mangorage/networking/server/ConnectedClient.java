package org.mangorage.networking.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private final Socket socket;

    ConnectedClient(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }


    public void disconnect() {

    }

    @Override
    public void run() {
        try {
            internalRun();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024]; // Adjust buffer size as needed
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream.toByteArray();
    }

    private void internalRun() throws IOException {
        while (socket.isConnected()) {
            var s = socket;
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();

            byte[] buffer = new byte[25565];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1 && socket.isConnected()) {
                System.out.println("Server received: " + bytesRead + " bytes");
            }

            s.close();
            System.out.println("Client disconnected");
        }

    }
}
