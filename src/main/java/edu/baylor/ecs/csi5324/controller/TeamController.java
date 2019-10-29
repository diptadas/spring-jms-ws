package edu.baylor.ecs.csi5324.controller;

import edu.baylor.ecs.csi5324.exception.TeamFilled;
import edu.baylor.ecs.csi5324.model.Team;
import edu.baylor.ecs.csi5324.request.CreateTeamRequest;
import edu.baylor.ecs.csi5324.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.findAllTeams();
    }

    @PostMapping
    public Team createTeam(@RequestBody CreateTeamRequest createTeamRequest) throws TeamFilled {
        return teamService.createTeam(createTeamRequest);
    }
}
