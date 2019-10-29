package edu.baylor.ecs.csi5324.service;

import edu.baylor.ecs.csi5324.model.Contest;
import edu.baylor.ecs.csi5324.model.Person;
import edu.baylor.ecs.csi5324.model.PersonType;
import edu.baylor.ecs.csi5324.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The {@link PopulateDataService} class populates initial data.
 * It inserts 3 teams, 9 contestants, 3 coaches, 1 manager and 2 contests.
 *
 * @author Dipta Das
 */

@Component
@Transactional
@Slf4j
public class PopulateDataService implements CommandLineRunner {
    private final EntityManager entityManager;

    public PopulateDataService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // month is 0 based
    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    // a fixed birth date for everyone
    // JANUARY 01, 2000
    public static Date getInitialDate() {
        return getDate(2000, Calendar.JANUARY, 1);
    }

    public void run(String... args) {
        log.info("Initializing data");

        List<Person> contestants = populateContestants(9);
        List<Person> coaches = populateCoaches(3);
        List<Person> managers = populateManagers(2);
        List<Team> teams = populateTeams(3, contestants, coaches);

        Contest contest = populateContest(0, managers.get(0), null);
        Contest subContest = populateContest(1, managers.get(1), contest);
    }

    private Contest populateContest(int index, Person manager, Contest parentContest) {
        Contest contest = new Contest(
                "contest-" + index,
                100,
                getDate(2019, Calendar.DECEMBER, 1),
                getDate(2019, Calendar.OCTOBER, 1),
                getDate(2019, Calendar.NOVEMBER, 1),
                false
        );
        if (manager != null) {
            contest.getManagers().add(manager);
        }
        if (parentContest != null) {
            contest.setParentContest(parentContest);
        }
        entityManager.persist(contest);
        return contest;
    }

    private List<Person> populateContestants(int number) {
        List<Person> contestants = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Person contestant = new Person(
                    "contestant-" + i,
                    getInitialDate(),
                    "contestant" + i + "@baylor.edu",
                    "baylor",
                    PersonType.CONTESTANT
            );
            contestants.add(contestant);
            entityManager.persist(contestant);
        }
        return contestants;
    }

    private List<Person> populateCoaches(int number) {
        List<Person> coaches = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Person coach = new Person(
                    "coach-" + i,
                    getInitialDate(),
                    "coach" + i + "@baylor.edu",
                    "baylor",
                    PersonType.COACH
            );
            coaches.add(coach);
            entityManager.persist(coach);
        }
        return coaches;
    }

    private List<Person> populateManagers(int number) {
        List<Person> managers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Person manager = new Person(
                    "manager-" + i,
                    getInitialDate(),
                    "manager" + i + "@baylor.edu",
                    "baylor",
                    PersonType.MANAGER
            );
            managers.add(manager);
            entityManager.persist(manager);
        }
        return managers;
    }

    // take 3 from contestants and 1 from coaches for creating a team
    private List<Team> populateTeams(int number, List<Person> contestants, List<Person> coaches) {
        List<Team> teams = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            Team team = new Team("team-" + i);
            team.setCoach(coaches.get(i));

            for (int j = 0; j < 3; j++) {
                team.getContestants().add(contestants.get(i * 3 + j));
            }

            teams.add(team);
            entityManager.persist(team);
        }

        return teams;
    }
}
