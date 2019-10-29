package edu.baylor.ecs.sender.jms;

import edu.baylor.ecs.csi5324.message.JmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(JmsMessage jmsMessage) {
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", jmsMessage);
    }
}
