package edu.baylor.ecs.csi5324.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Person} class is a JPA entity that stores information of a person.
 *
 * @author Dipta Das
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private Date birthDate;

    @Column
    @NotNull
    @Email(message = "Email should be valid")
    private String email;

    @Column
    @NotNull
    private String university;

    @Column
    @NotNull
    private PersonType type;

    @ManyToMany(mappedBy = "contestants")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "coach")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Team> coachedTeams = new HashSet<>();

    @ManyToMany(mappedBy = "managers")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Contest> managedContests = new HashSet<>();

    public void removeAssociations() {
        for (Team team : this.getTeams()) {
            team.getContestants().remove(this);
        }

        for (Team team : this.getCoachedTeams()) {
            team.setCoach(null);
        }

        for (Contest contest : this.getManagedContests()) {
            contest.getManagers().remove(this);
        }

        this.teams.clear();
        this.coachedTeams.clear();
        this.managedContests.clear();
    }

    // create with auto generated ID
    public Person(@NotNull String name, @NotNull Date birthDate, @NotNull @Email(message = "Email should be valid") String email, @NotNull String university, @NotNull PersonType type) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.university = university;
        this.type = type;
    }
}