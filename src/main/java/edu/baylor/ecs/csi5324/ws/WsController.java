package edu.baylor.ecs.csi5324.ws;

import edu.baylor.ecs.csi5324.model.ConsumeMessage;
import edu.baylor.ecs.csi5324.model.ProduceMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WsController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ProduceMessage reply(ConsumeMessage message) throws Exception {
        log.info("Received " + message);
        Thread.sleep(1000); // simulated delay
        return new ProduceMessage("Hello, " + message.getContent());
    }

}
