package com.ruk.http.usage.example;

import com.ruk.server.api.EchoHttpServerAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoServer {
    protected static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);

    public static void main(String args[]) {
        startServer();
    }

    public static void startServer() {
        Thread thread = new Thread(() -> {
            EchoHttpServerAPI httpServerAPI = new EchoHttpServerAPI();
            try {
                httpServerAPI.startServer(9090);
            } catch (InterruptedException e) {
                LOG.error(" Error in example EchoServer. ", e);
            }
        });
        thread.start();
    }
}
