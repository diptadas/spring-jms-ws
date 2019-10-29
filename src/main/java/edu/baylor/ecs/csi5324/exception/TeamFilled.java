package edu.baylor.ecs.csi5324.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@link TeamFilled} indicates registration is not allowed for a particular {@link edu.baylor.ecs.csi5324.model.Contest}.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TeamFilled extends Exception {

    private static final long serialVersionUID = 1L;

    public TeamFilled() {
        super("maximum number of contestant is already added to the team");
    }

    public TeamFilled(String message) {
        super(message);
    }
}
