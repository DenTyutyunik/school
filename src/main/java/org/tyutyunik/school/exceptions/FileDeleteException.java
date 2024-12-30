package org.tyutyunik.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT, reason = "File Delete Exception")
public class FileDeleteException extends RuntimeException {
    public FileDeleteException(Class<?> className, Long id, String path) {
        super("[EXCEPTION] [%s]: File didn't delete for studentId [%s] by path [%s]".formatted(className.getSimpleName(), path, id));
    }
}
