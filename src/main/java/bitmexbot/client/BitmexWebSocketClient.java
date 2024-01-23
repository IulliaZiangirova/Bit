package bitmexbot.client;

import bitmexbot.util.SignatureCreator;
import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Signature;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ClientEndpoint
@Slf4j
public class BitmexWebSocketClient {
    private final String serverUri = "wss://ws.testnet.bitmex.com/realtime";
    private Session session;
    private WebSocketContainer container;

    private String userId;
    private Boolean isConnected;
    private String apiKey = "GfibqQZKf1KvKJJ4BwK63-QJ";
    private String apiSecret = "i_dE1FKK42t64fB7qMLcaYL0xfe3yqaR2LqouYqf-HM02QCP";


    public void connect(){
        try{
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI(serverUri));
            session.setMaxIdleTimeout(TimeUnit.MINUTES.toMillis(60));
            if(session.isOpen()){
                isConnected = true;
                log.info("Session is open");
                SignatureCreator signature = new SignatureCreator();
                long expires = System.currentTimeMillis() / 1000 + 5;
                String signatureStr = signature.getSignature(apiSecret, "GET/realtime" + expires);
                session.getBasicRemote().sendText("{\"op\": \"authKeyExpires\", \"args\": [\"" + apiKey + "\", " + expires + ", \"" + signatureStr + "\" ]}\n");
            }}
        catch (URISyntaxException| IOException| DeploymentException e){
            log.error("Cannot connect to the simulator server.");
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.session = userSession;
    }


    @OnMessage
    public void onMessage(String message){
        System.out.println(message);

    }

    @OnMessage
    public void onMessage(PongMessage pongMessage){

    }

    @OnError
    public void onError(Throwable error){
        log.info("error: " + error.getMessage());

    }

    public void stopWebSocketThreads(){

    }

    public void ping(){}


}
