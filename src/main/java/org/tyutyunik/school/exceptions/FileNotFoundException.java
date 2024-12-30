package org.tyutyunik.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.Path;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File Not Found Exception")
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(Class<?> className, Path path) {
        super("[EXCEPTION] [%s]: File Not Found by path [%s]".formatted(className.getSimpleName(), path));
    }
}
