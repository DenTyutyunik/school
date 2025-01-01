package org.tyutyunik.school.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.Path;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "[EXCEPTION] File Not Found")
public class FileNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(FileNotFoundException.class);

    public FileNotFoundException(Class<?> className, Path path) {
        String content = String.format("[ERROR] [%s]: File Not Found by path [%s]", className.getSimpleName(), path);
        super(content);
        logger.error(content);
    }
}
