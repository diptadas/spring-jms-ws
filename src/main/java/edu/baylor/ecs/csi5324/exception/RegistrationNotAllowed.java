package edu.baylor.ecs.csi5324.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The {@link RegistrationNotAllowed} indicates registration is not allowed for a particular {@link edu.baylor.ecs.csi5324.model.Contest}.
 *
 * @author Dipta Das
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RegistrationNotAllowed extends Exception {

    private static final long serialVersionUID = 1L;

    public RegistrationNotAllowed() {
        super("registration is blocked for this contest");
    }

    public RegistrationNotAllowed(String message) {
        super(message);
    }
}
