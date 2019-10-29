package edu.baylor.ecs.csi5324.service;

import edu.baylor.ecs.csi5324.exception.RecordNotFoundException;
import edu.baylor.ecs.csi5324.exception.RegistrationNotAllowed;
import edu.baylor.ecs.csi5324.model.Contest;
import edu.baylor.ecs.csi5324.model.Person;
import edu.baylor.ecs.csi5324.model.Team;
import edu.baylor.ecs.csi5324.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Service
public class ContestService {
    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private TeamService teamService;

    public Contest save(Contest contest) {
        return contestRepository.save(contest);
    }

    public List<Contest> findAllContests() {
        return contestRepository.findAll();
    }

    public Contest findContestByName(String name) throws RecordNotFoundException {
        List<Contest> contests = contestRepository.findByName(name);
        if (contests.size() != 1) throw new RecordNotFoundException("contest not found");
        return contests.get(0);
    }

    public void promoteTeam(Team team, Contest subContest) throws RegistrationNotAllowed {
        if (team.getRank() >= 1 && team.getRank() <= 5) {
            Contest superContest = subContest.getParentContest();
            team.setRank(0); // reset rank
            performRegistration(superContest, team);
        }
    }

    public void allowAndPerformRegistrationByName(String teamName, String contestName) throws RegistrationNotAllowed, RecordNotFoundException {
        Contest contest = findContestByName(contestName);
        Team team = teamService.findTeamByName(teamName);

        contest.setRegistrationAllowed(true);

        performRegistration(contest, team);
    }

    public void performRegistration(Contest contest, Team team) throws RegistrationNotAllowed {
        // check registration constraints

        if (!contest.isRegistrationAllowed()) {
            throw new RegistrationNotAllowed();
        }

        if (contest.getRegisteredTeams().size() >= contest.getCapacity()) {
            throw new RegistrationNotAllowed("registration full");
        }

        if (team.getContestants().size() != 3) {
            throw new RegistrationNotAllowed("exactly 3 contestant needed");
        }

        if (team.getCoach() == null) {
            throw new RegistrationNotAllowed("one coach needed");
        }

        // check age all contestants
        for (Person contestant : team.getContestants()) {
            int age = getAgeFromDob(contestant.getBirthDate());
            if (age > 24) {
                throw new RegistrationNotAllowed("all contestant must be 24 years or younger");
            }
        }

        // check team is unique by name
        for (Team registeredTeam : contest.getRegisteredTeams()) {
            if (registeredTeam.getName().equals(team.getName())) {
                throw new RegistrationNotAllowed("team is already registered to this contest");
            }
        }

        // perform registration now
        contest.performRegistration(team);

        contestRepository.save(contest); // detached entity error
    }

    private int getAgeFromDob(Date dob) {
        LocalDate dobLocal = new java.sql.Date(dob.getTime()).toLocalDate();
        return Period.between(dobLocal, LocalDate.now()).getYears();
    }
}
