package edu.baylor.ecs.csi5324.ws;

import com.google.gson.Gson;
import edu.baylor.ecs.csi5324.model.ProduceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class RawWsHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received " + message.getPayload());

        try {
            broadcast(new ProduceMessage("generic reply"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void broadcast(ProduceMessage message) throws IOException {
        TextMessage textMessage = new TextMessage(new Gson().toJson(message, ProduceMessage.class));
        log.info("Sending " + textMessage.getPayload());

        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(textMessage);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Connected");
        sessions.add(session);
    }
}
