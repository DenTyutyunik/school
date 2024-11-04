package org.tyutyunik.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Already Added Exception")
public class AlreadyAddedException extends RuntimeException {
    public AlreadyAddedException() {
        super("[EXCEPTION] Already Added");
    }
}
