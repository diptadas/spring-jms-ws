package edu.baylor.ecs.csi5324.jms;

import edu.baylor.ecs.csi5324.model.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(Email email) {
        log.info("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", email);
    }
}
