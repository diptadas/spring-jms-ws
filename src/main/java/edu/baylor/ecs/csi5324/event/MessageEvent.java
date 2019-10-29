package edu.baylor.ecs.csi5324.event;

import edu.baylor.ecs.csi5324.model.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class MessageEvent extends ApplicationEvent {
    private Email email;

    public MessageEvent(Object source, Email email) {
        super(source);
        this.email = email;
    }
}
