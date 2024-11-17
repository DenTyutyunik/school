package org.tyutyunik.school.service;

import org.tyutyunik.school.exceptions.*;
import org.tyutyunik.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;

public interface FacultyService {
    /**
     * Create faculty
     *
     * @param faculty
     * @return Faculty
     * @throws IsNotValid
     */
    Long create(Faculty faculty) throws IsNotValid;

    /**
     * Read faculty
     *
     * @param id
     * @return Faculty
     * @throws IsNotValid
     * @throws NotFoundException
     */
    Faculty read(Long id) throws IsNotValid, NotFoundException;

    /**
     * Read everything faculty
     *
     * @return Collection of Faculty
     * @throws NotFoundException
     */
    Collection<Faculty> readAll() throws NotFoundException;

    /**
     * Update faculty
     *
     * @param name
     * @param color
     * @return Faculty
     * @throws AlreadyAddedException
     * @throws IsNotValid
     * @throws NotFoundException
     */
    Faculty update(Long id, Faculty faculty) throws AlreadyAddedException, IsNotValid, NotFoundException;

    /**
     * Delete faculty
     *
     * @param id
     * @return Faculty
     * @throws IsNotValid
     * @throws NotFoundException
     */
    Faculty delete(Long id) throws IsNotValid, NotFoundException;

    /**
     * Filter faculty by
     *
     * @param color
     * @return HashMap of [Long,Faculty]
     * @throws IsNotValid
     * @throws NotFoundException
     */
    HashMap<Long, Faculty> filterByColor(String color) throws NotFoundException;
}
