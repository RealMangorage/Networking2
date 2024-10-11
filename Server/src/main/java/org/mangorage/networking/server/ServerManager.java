package org.mangorage.networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ServerManager extends Thread {
    private static final ServerManager INSTANCE = new ServerManager();

    public static ServerManager getInstance() {
        return INSTANCE;
    }

    public static void initStatic() {
        INSTANCE.start();
    }


    private final ServerSocket server;
    private final ExecutorService service = Executors.newFixedThreadPool(10);
    private final List<ConnectedClient> clients = new CopyOnWriteArrayList<>();

    private ServerManager() {
        try {
            this.server = new ServerSocket(1000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            internalRun();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void internalRun() throws IOException {
        while (true) {
            ConnectedClient connectedClient = new ConnectedClient(server.accept());
            clients.add(connectedClient);
            service.submit(connectedClient);
        }

    }

    public void disconnect(ConnectedClient client) {
        clients.remove(client);
    }
}

