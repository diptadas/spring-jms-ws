package edu.baylor.ecs.csi5324.repository;

import edu.baylor.ecs.csi5324.model.AgeGroup;
import edu.baylor.ecs.csi5324.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT NEW edu.baylor.ecs.csi5324.model.AgeGroup(DATEDIFF(YEAR, c.birthDate, CURRENT_DATE()), COUNT(c.id)) " +
            "FROM edu.baylor.ecs.csi5324.model.Person c GROUP BY DATEDIFF(YEAR, c.birthDate, CURRENT_DATE()) HAVING c.type = edu.baylor.ecs.csi5324.model.PersonType.CONTESTANT")
    List<AgeGroup> groupContestantByAge();

    Person findByName(String name);
}
