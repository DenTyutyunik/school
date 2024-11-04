package org.tyutyunik.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Is Not Valid")
public class IsNotValid extends RuntimeException {
    public IsNotValid() {
        super("[EXCEPTION] Is Not Valid");
    }
}
