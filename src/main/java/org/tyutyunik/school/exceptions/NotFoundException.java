package org.tyutyunik.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found Exception")
public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> className, Long id) {
        super("[EXCEPTION] [%s]: Not Found by id [%s]".formatted(className.getSimpleName(), id));
    }
}
