package com.ruk.http.usage.example;

import com.ruk.server.api.EchoHttpServerAPI;

public class EchoServer {

    public static void main(String args[]) {
        Thread thread = new Thread(() -> {
            EchoHttpServerAPI httpServerAPI = new EchoHttpServerAPI();
            try {
                httpServerAPI.startServer(9090);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //httpServerAPI.stopServer();
        });
        thread.start();
    }
}
