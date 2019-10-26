package edu.baylor.ecs.jms.model;

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
