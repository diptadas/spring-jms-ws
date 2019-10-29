package edu.baylor.ecs.csi5324.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateTeamRequest {
    private String teamName;
    private List<String> memberNames;
    private String coachName;
}
