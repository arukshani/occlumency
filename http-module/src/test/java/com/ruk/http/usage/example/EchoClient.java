package com.ruk.http.usage.example;

import com.ruk.client.api.EchoClientAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoClient {
    protected static final Logger LOG = LoggerFactory.getLogger(EchoClient.class);

    public static void main(String args[]) {
        EchoClientAPI clientAPI = new EchoClientAPI();
        try {
            clientAPI.connectToServer("localhost", 9090);
            clientAPI.writeToServer("Message 1 from client. ");
            clientAPI.setFinalMessage(true);
            clientAPI.writeToServer("Message 2 from client. ");
        } catch (Exception e) {
            LOG.error("Error in example EchoClient. ", e);
        }
    }
}
