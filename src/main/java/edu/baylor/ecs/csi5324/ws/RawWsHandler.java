package edu.baylor.ecs.csi5324.ws;

import com.google.gson.Gson;
import edu.baylor.ecs.csi5324.event.MessageEvent;
import edu.baylor.ecs.csi5324.model.ProduceMessage;
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
        log.info("Received " + message.getPayload());

        try {
            broadcast(new ProduceMessage("generic reply"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void broadcast(ProduceMessage message) throws IOException {
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
        try {
            broadcast(new ProduceMessage("connected"));
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
        try {
            broadcast(new ProduceMessage(messageEvent.getEmail().toString()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
