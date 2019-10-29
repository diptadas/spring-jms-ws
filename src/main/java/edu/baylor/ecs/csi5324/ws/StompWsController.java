package edu.baylor.ecs.csi5324.ws;

import edu.baylor.ecs.csi5324.message.WsConsumeMessage;
import edu.baylor.ecs.csi5324.message.WsProduceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class StompWsController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public WsProduceMessage reply(WsConsumeMessage message) throws Exception {
        log.info("Received " + message);
        Thread.sleep(1000); // simulated delay
        return new WsProduceMessage("Hello, " + message.getContent());
    }

}
