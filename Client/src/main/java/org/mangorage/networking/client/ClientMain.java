package org.mangorage.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket s = new Socket("localhost", 1000);
        s.getOutputStream().write(new byte[25565]);
        s.getOutputStream().flush();

        Thread.sleep(10000);

        s.close();
    }
}
