package org.tyutyunik.school.service;

import org.tyutyunik.school.model.Student;

import java.util.Collection;

public interface StudentService {
    /**
     * Create student
     */
    Student create(Student student);

    /**
     * Read student
     */
    Student read(Long id);

    /**
     * Read everyone student
     */
    Collection<Student> readAll();

    /**
     * Read the 5 last student
     */
    Collection<Student> readLast5();

    /**
     * Update student
     */
    Student update(Long id, Student student);

    /**
     * Delete student
     */
    Student delete(Long id);

    /**
     * Count everyone student
     */
    Long countAll();

    /**
     * Get average age of everyone student
     */
    Long ageAvg();

    /**
     * Get average age of everyone student (Stream)
     */
    Long ageAvg2();

    /**
     * Filter student by name starting with A and sorting by alphabetical order
     */
    Collection<Student> filterByNameAlphabeticalOrderStartingWithA();

    /**
     * Filter student by age
     */
    Collection<Student> filterByAge(int age);

    /**
     * Filter student by age between
     */
    Collection<Student> filterByAgeBetween(int ageMin, int ageMax);

    /**
     * Doesn't matter
     */
    Integer doesNotMatter();

    /**
     * Doesn't matter version 2 optimized
     */
    Integer doesNotMatter2();

    /**
     * Read everyone student in parallel thread
     */
    Collection<Student> readAllParallel();

    /**
     * Read everyone student in synchronized thread
     */
    Collection<Student> readAllSynchronized();
}
