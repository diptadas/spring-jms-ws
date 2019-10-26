package edu.baylor.ecs.csi5324.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Email {
    private String to;
    private String body;
}
