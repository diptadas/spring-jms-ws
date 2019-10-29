package edu.baylor.ecs.csi5324.jms;

import edu.baylor.ecs.csi5324.event.MessageEvent;
import edu.baylor.ecs.csi5324.model.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        log.info("Received <" + email + ">");

        log.info("Creating event...");
        applicationEventPublisher.publishEvent(new MessageEvent(this, email));
    }
}
