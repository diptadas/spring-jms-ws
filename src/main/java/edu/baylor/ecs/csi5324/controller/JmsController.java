package edu.baylor.ecs.csi5324.controller;

import edu.baylor.ecs.csi5324.jms.Sender;
import edu.baylor.ecs.csi5324.message.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jms")
public class JmsController {
    @Autowired
    private Sender sender;

    @PostMapping("/send")
    public void sendJmsMessage(@RequestBody JmsMessage jmsMessage) {
        sender.send(jmsMessage);
    }
}
