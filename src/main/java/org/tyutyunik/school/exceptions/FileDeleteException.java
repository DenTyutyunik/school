package org.tyutyunik.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "[EXCEPTION] File Delete Exception")
public class FileDeleteException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(FileDeleteException.class);

    public FileDeleteException(Class<?> className, Long id, String path) {
        String content = String.format("[EXCEPTION] [%s]: File didn't delete for studentId [%s] by path [%s]", className.getSimpleName(), id, path);
        super(content);
        logger.error(content);
    }
}
