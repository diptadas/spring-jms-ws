package edu.baylor.ecs.csi5324.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Team} class is a JPA entity that stores information of a team.
 * It is associated with three {@link Person} (as CONTESTANT) and one {@link Person} (as COACH).
 * It contains a {@link State} that is PENDING by default.
 *
 * @author Dipta Das
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column(columnDefinition = "integer default 0")
    private int rank = 0;

    @Column(columnDefinition = "varchar default 'PENDING'")
    @NotNull
    private State state = State.PENDING;

    @ManyToMany()
    @JoinTable(
            name = "team_member",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @Size(max = 3, message = "a team can have maximum 3 contestants")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Person> contestants = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "person_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Person coach;

    @ManyToMany()
    @JoinTable(
            name = "team_registration",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "contest_id"))
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Contest> registeredContests = new HashSet<>();

    // create with auto generated ID
    public Team(@NotNull String name) {
        this.name = name;
    }
}