package org.tyutyunik.school.service;

import org.tyutyunik.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    /**
     * Create faculty
     */
    Faculty create(Faculty faculty);

    /**
     * Read faculty
     */
    Faculty read(Long id);

    /**
     * Read everything faculty
     */
    Collection<Faculty> readAll();

    /**
     * Update faculty
     */
    Faculty update(Long id, Faculty faculty);

    /**
     * Delete faculty
     */
    Faculty delete(Long id);

    /**
     * Filter faculty by color
     */
    Collection<Faculty> filterByColor(String color);

    /**
     * Filter faculty by name
     */
    Collection<Faculty> filterByName(String name);

    /**
     * Filter faculty by longest name
     */
    Faculty filterByNameLongest();
}
