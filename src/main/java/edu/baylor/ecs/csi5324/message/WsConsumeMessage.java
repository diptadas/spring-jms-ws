package edu.baylor.ecs.csi5324.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WsConsumeMessage {
    private String content;
}
