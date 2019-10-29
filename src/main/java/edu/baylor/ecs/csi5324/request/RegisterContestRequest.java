package edu.baylor.ecs.csi5324.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterContestRequest {
    private String teamName;
    private String contestName;
}
