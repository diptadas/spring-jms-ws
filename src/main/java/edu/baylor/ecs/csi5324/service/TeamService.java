package edu.baylor.ecs.csi5324.service;

import edu.baylor.ecs.csi5324.exception.RecordNotFoundException;
import edu.baylor.ecs.csi5324.exception.TeamFilled;
import edu.baylor.ecs.csi5324.model.Person;
import edu.baylor.ecs.csi5324.model.Team;
import edu.baylor.ecs.csi5324.repository.PersonRepository;
import edu.baylor.ecs.csi5324.repository.TeamRepository;
import edu.baylor.ecs.csi5324.request.CreateTeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonRepository personRepository;

    public void save(Team team) throws TeamFilled {
        if (team.getContestants().size() > 3) {
            throw new TeamFilled();
        }
        teamRepository.save(team);
    }

    public Team createTeam(CreateTeamRequest createTeamRequest) throws TeamFilled {
        Team team = new Team(createTeamRequest.getTeamName());

        Person coach = personRepository.findByName(createTeamRequest.getCoachName());
        addCoachToTeam(team, coach);

        for (String name : createTeamRequest.getMemberNames()) {
            Person contestant = personRepository.findByName(name);
            addContestantToTeam(team, contestant);
        }

        return team;
    }

    public Team findTeamById(long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    public void addContestantToTeam(Team team, Person contestant) throws TeamFilled {
        if (team.getContestants().size() >= 3) {
            throw new TeamFilled();
        } else {
            team.getContestants().add(contestant);
            contestant.getTeams().add(team);
            teamRepository.save(team);
        }
    }

    public void addCoachToTeam(Team team, Person coach) throws TeamFilled {
        if (team.getCoach() != null) {
            throw new TeamFilled();
        } else {
            team.setCoach(coach);
            coach.getCoachedTeams().add(team);
            teamRepository.save(team);
        }
    }

    public Team findTeamByName(String name) throws RecordNotFoundException {
        List<Team> teams = teamRepository.findByName(name);
        if (teams.size() != 1) throw new RecordNotFoundException("team not found");
        return teams.get(0);
    }
}
