package edu.baylor.ecs.csi5324.ws;

import com.google.gson.Gson;
import edu.baylor.ecs.csi5324.event.MessageEvent;
import edu.baylor.ecs.csi5324.message.WsProduceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class RawWsHandler extends TextWebSocketHandler implements ApplicationListener<MessageEvent> {

    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received message from ws client: " + message.getPayload());

        try {
            broadcast(new WsProduceMessage("generic reply"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void broadcast(WsProduceMessage message) throws IOException {
        TextMessage textMessage = new TextMessage(new Gson().toJson(message, WsProduceMessage.class));
        log.info("Sending message to ws clients: " + textMessage.getPayload());

        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(textMessage);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Ws client connected");
        sessions.add(session);
        try {
            broadcast(new WsProduceMessage("connected"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Closed");
        sessions.remove(session);
    }

    @Override
    public void onApplicationEvent(MessageEvent messageEvent) {
        log.info("Received event...");
        try {
            broadcast(new WsProduceMessage(messageEvent.getJmsMessage().toString()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
