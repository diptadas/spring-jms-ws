package edu.baylor.ecs.csi5324.event;

import edu.baylor.ecs.csi5324.message.JmsMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class MessageEvent extends ApplicationEvent {
    private JmsMessage jmsMessage;

    public MessageEvent(Object source, JmsMessage jmsMessage) {
        super(source);
        this.jmsMessage = jmsMessage;
    }
}
