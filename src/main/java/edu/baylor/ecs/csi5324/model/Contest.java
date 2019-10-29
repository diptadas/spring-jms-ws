package edu.baylor.ecs.csi5324.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.baylor.ecs.csi5324.exception.RegistrationNotAllowed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Contest} class is a JPA entity that stores information of a contest.
 * It is associated with zero or more {@link Team} and one or more {@link Person} (as MANAGER).
 * It is also associated with other {@link Contest} as sub-contests.
 *
 * @author Dipta Das
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    @Min(value = 1, message = "Capacity should be minimum 1")
    private int capacity;

    @Column
    @NotNull
    private Date date;

    @Column
    @NotNull
    private Date registrationFrom;

    @Column
    @NotNull
    private Date registrationTo;

    @Column
    private boolean registrationAllowed;

    @ManyToOne
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Contest parentContest;

    @OneToMany(mappedBy = "parentContest")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Contest> subContests = new HashSet<>();

    @ManyToMany()
    @JoinTable(
            name = "contest_managers",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Person> managers = new HashSet<>();

    @ManyToMany(mappedBy = "registeredContests")
    /*@JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)*/
    private Set<Team> registeredTeams = new HashSet<>();

    // create with auto generated ID
    public Contest(@NotNull String name, @NotNull @Min(1) int capacity, @NotNull Date date, @NotNull Date registrationFrom, @NotNull Date registrationTo, boolean registrationAllowed) {
        this.name = name;
        this.capacity = capacity;
        this.date = date;
        this.registrationFrom = registrationFrom;
        this.registrationTo = registrationTo;
        this.registrationAllowed = registrationAllowed;
    }

    public void performRegistration(Team team) throws RegistrationNotAllowed {
        if (registrationAllowed) {
            registeredTeams.add(team);
            team.getRegisteredContests().add(this); // ensures bidirectional insert
        } else throw new RegistrationNotAllowed();
    }
}