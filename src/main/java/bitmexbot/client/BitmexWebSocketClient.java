package bitmexbot.client;

import bitmexbot.service.BotExecutor;
import bitmexbot.util.JsonCreator;
import bitmexbot.util.SignatureCreator;
import jakarta.websocket.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.tyrus.spi.ClientContainer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Signature;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ClientEndpoint
@Slf4j
@Data
public class BitmexWebSocketClient {
    private final String serverUri = "wss://ws.testnet.bitmex.com/realtime?subscribe";
    private final String serverUri1 = "wss://ws.testnet.bitmex.com/realtime?subscribe=order";
    private Session session;
    private WebSocketContainer container;
    private String userId;
    private Boolean isConnected;
    private String apiKey = "GfibqQZKf1KvKJJ4BwK63-QJ";
    private String apiSecret = "i_dE1FKK42t64fB7qMLcaYL0xfe3yqaR2LqouYqf-HM02QCP";
    private BotExecutor botExecutor;


    public void connect(){
        try{
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI(serverUri));
            if(session.isOpen()){
               isConnected = true;
                log.info("Session is open");
               SignatureCreator signature = new SignatureCreator();
                long expires = System.currentTimeMillis() / 1000 + 7;
                String signatureStr = signature.getSignature(apiSecret, "GET/realtime" + expires);
                String authRequest = "{\"op\":\"authKeyExpires\",\"args\":[\"" + apiKey + "\"," + expires + ",\"" + signatureStr + "\"]}\n";
                session.getBasicRemote().sendText(authRequest);
                String orderRequest = "{\"op\": \"subscribe\", \"args\": \"order\"}";
                session.getBasicRemote().sendText(orderRequest);
                session.getBasicRemote().sendText("{\"op\": \"subscribe\", \"args\": \"trade:XBTUSD\"}");
                session.getBasicRemote().sendText("{\"op\": \"unsubscribe\", \"args\": \"trade:XBTUSD\"}");
           }
    }
        catch (URISyntaxException| IOException| DeploymentException e){
            log.error("Cannot connect to the simulator server.");
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("Connected to BitMEX API WebSocket");
        this.session = userSession;
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println("--");
        System.out.println(message);
        botExecutor.parsData(message);
    }

    @OnMessage
    public void onMessage(PongMessage pongMessage){
    }

    @OnError
    public void onError(Throwable error){
        log.info("error: " + error.getMessage());
    }

    @OnClose
    public void onClose(CloseReason closeReason) {
        System.out.println("Disconnected from BitMEX API WebSocket: " + closeReason);
        this.session = null;
    }


    public void disconnect() {
        if (this.session != null) {
            try {
                this.session.close();
            } catch (Exception e) {
                System.err.println("Error while closing WebSocket session: " + e.getMessage());
            }
        }
    }

    public void ping(){}

}
