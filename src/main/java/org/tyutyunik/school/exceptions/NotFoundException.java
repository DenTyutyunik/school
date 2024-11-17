package org.tyutyunik.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not Found Exception")
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("[EXCEPTION] Not Found");
    }
}
