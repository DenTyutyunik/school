package org.tyutyunik.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "[EXCEPTION] Already Added")
public class AlreadyAddedException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(AlreadyAddedException.class);

    public AlreadyAddedException(Class<?> className, Long id) {
        String content = String.format("[ERROR] [%s]: Already Added by id [%s]", className.getSimpleName(), id);
        super(content);
        logger.error(content);
    }
}
