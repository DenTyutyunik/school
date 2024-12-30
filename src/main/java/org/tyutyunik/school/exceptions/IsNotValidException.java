package org.tyutyunik.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Is Not Valid")
public class IsNotValidException extends RuntimeException {
    public IsNotValidException(Class<?> className, Long id, String message) {
        super("[EXCEPTION] [%s]: [%s] Is Not Valid. [%s]".formatted(className.getSimpleName(), id, message));
    }
}
