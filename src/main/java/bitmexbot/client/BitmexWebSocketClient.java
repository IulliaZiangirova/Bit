package bitmexbot.client;

import bitmexbot.service.BotExecutor;
import bitmexbot.util.Endpoints;
import bitmexbot.util.SignatureCreator;
import jakarta.websocket.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
@Slf4j
@Data
public class BitmexWebSocketClient {
    //private final String serverUri = "wss://ws.testnet.bitmex.com/realtime?subscribe";
    private Session session;
    private WebSocketContainer container;
    private String userId;
    private Boolean isConnected;
    private final String apiKey;
    private final String apiSecret;
    private BotExecutor botExecutor;
    private final ScheduledExecutorService pingTimer =Executors.newSingleThreadScheduledExecutor();


    public BitmexWebSocketClient(String apiKey, String apiSecret){
        this.apiSecret = apiSecret;
        this.apiKey = apiKey;
    }

    public void connect(){
        try{
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, new URI(Endpoints.WEBSOCKET_URI));
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
        pingTimer.scheduleAtFixedRate(() -> {
            sendPing();
        }, 0, 20, TimeUnit.SECONDS);
    }



    @OnMessage
    public void onMessage(String message){
        System.out.println("--");
        System.out.println(message);
        botExecutor.parsData(message);
    }

    @OnMessage
    public void onMessagePong(PongMessage pongMessage){
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


    private void sendPing() {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendPing(ByteBuffer.wrap(new byte[0]));
            }
        } catch (IOException e) {
            log.error("Error on sending ping: ", e);
        }
    }






    // Health check ping/pong
//    private void launchPingTimer() {
//        if (pingTimer == null) {
//            log.info("Started Ping timer");
//            pingTimer = Executors.newSingleThreadScheduledExecutor()
//            pingTimer.scheduleWithFixedDelay(() -> {
//                long l = lastMessageTime.get();
//                if (System.currentTimeMillis() - l < MAX_MSG_DELAY + 2000) {
//                    sendPing();
//                } else if(isConnected.get()) {
//                    scheduler.schedule(() -> reconnect(false), RECONNECT_SECONDS, TimeUnit.SECONDS);
//                }
//
//            }, 0, MAX_MSG_DELAY, TimeUnit.MILLISECONDS);
//        }
//        if(orderValidatorTimer == null) {
//            orderValidatorTimer = Executors.newSingleThreadScheduledExecutor();
//            orderValidatorTimer.scheduleWithFixedDelay(this::sendOrderIdsForValidation,
//                    0, MAX_MSG_DELAY, TimeUnit.MILLISECONDS);
//        }
//    }


//    public void connect() {
//        try {
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//            session = container.connectToServer(this, new URI(serverUri));
//            session.setMaxIdleTimeout(TimeUnit.MINUTES.toMillis(60));
//            if (session.isOpen()) {
//                isConnected = true;
//                log.info("Session is opened");
//                Signature signature = new Signature();
//                long expires = System.currentTimeMillis() / 1000 + 5;
//                String signatureStr = signature.getSignature(apiSecret, "GET/realtime" + expires);
//                session.getBasicRemote().sendText("{\"op\": \"authKeyExpires\", \"args\": [\"" + apiKey +"\", " + expires + ", \""+ signatureStr +"\"]}\n");
//            }
//
//            //      launchPingTimer();
//
//        } catch (URISyntaxException | IOException | DeploymentException e) {
//            log.error("Cannot connect to the simulator server.");
//        }
//    }

//    private void reconnect(boolean isLogin) {
//        if (isConnected.get()) {
//            isConnected.set(false);
//            adminListeners.forEach(l -> l.onConnectionLost(DisconnectionReason.UNKNOWN,
//                    "Not able to reach the Simulator server"));
//        }
//        if (!scheduler.isShutdown()) {
//            // Reconnect
//            stopWebsocketThreads();
//            Log.error("Trying to reconnect to Simulator server in " + RECONNECT_SECONDS + " secs");
//            scheduler.schedule(() -> connect(false), RECONNECT_SECONDS, TimeUnit.SECONDS);
//        }
//    }













}


