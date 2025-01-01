package org.tyutyunik.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "[EXCEPTION] Not Found")
public class NotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(NotFoundException.class);

    public NotFoundException(Class<?> className, Long id) {
        String content = String.format("[ERROR] [%s]: Not Found by id %s", className.getSimpleName(), id);
        super(content);
        logger.error(content);
    }
}
