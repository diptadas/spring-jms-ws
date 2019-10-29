package edu.baylor.ecs.sender.controller;

import edu.baylor.ecs.csi5324.message.JmsMessage;
import edu.baylor.ecs.sender.jms.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private Sender sender;

    @PostMapping("/send")
    public void sendEmail(@RequestBody JmsMessage jmsMessage) {
        sender.send(jmsMessage);
    }
}
