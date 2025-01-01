package org.tyutyunik.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "[EXCEPTION] Is Not Valid")
public class IsNotValidException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(IsNotValidException.class);

    public IsNotValidException(Class<?> className, Long id, String message) {
        String content = String.format("[ERROR] [%s]: [%s] Is Not Valid. [%s]", className.getSimpleName(), id, message);
        super(content);
        logger.error(content);
    }
}
