package com.sprint.v1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    private static final Logger LOG = LogManager.getLogger(Server.class);
    public static void main(String[] args) {
        ArrayList<Echoer> threadList = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            do {
                Socket socket = serverSocket.accept();
                Echoer echoer = new Echoer(socket, threadList);
                threadList.add(echoer);
                threadPoolExecutor.execute(new Echoer(socket, threadList));
           } while (true);

        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
            LOG.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
