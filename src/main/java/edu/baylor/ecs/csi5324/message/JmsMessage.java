package edu.baylor.ecs.csi5324.message;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JmsMessage {
    private String to;
    private String body;
}
