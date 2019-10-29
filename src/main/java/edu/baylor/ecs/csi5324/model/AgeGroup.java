package edu.baylor.ecs.csi5324.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@link AgeGroup} class stores the results of a custom query
 * that fetches number of {@link Person} with type CONTESTANT group by age.
 * This is not a JPA entity class.
 *
 * @author Dipta Das
 */

@Getter
@Setter
@AllArgsConstructor
public class AgeGroup {
    private int age;
    private long count;
}
