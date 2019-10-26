package edu.baylor.ecs.jms.jms;

import edu.baylor.ecs.jms.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(Email email) {
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", email);
    }
}
