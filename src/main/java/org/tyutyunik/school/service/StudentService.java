package org.tyutyunik.school.service;

import org.tyutyunik.school.exceptions.*;
import org.tyutyunik.school.model.Student;

import java.util.Collection;
import java.util.HashMap;

public interface StudentService {
    /**
     * Create student
     *
     * @param student
     * @return Student
     * @throws IsNotValid
     */
    Student create(Student student) throws IsNotValid;

    /**
     * Read student
     *
     * @param id
     * @return Student
     * @throws IsNotValid
     * @throws NotFoundException
     */
    Student read(Long id) throws IsNotValid, NotFoundException;

    /**
     * Read everything student
     *
     * @return Collection of Student
     * @throws NotFoundException
     */
    Collection<Student> readAll() throws NotFoundException;

    /**
     * Update student
     *
     * @param id
     * @param student
     * @return Student
     * @throws AlreadyAddedException
     * @throws IsNotValid
     * @throws NotFoundException
     */
    Student update(Long id, Student student) throws AlreadyAddedException, IsNotValid, NotFoundException;

    /**
     * Delete student
     *
     * @param id
     * @return Student
     * @throws IsNotValid
     * @throws NotFoundException
     */
    Student delete(Long id) throws IsNotValid, NotFoundException;

    /**
     * Filter student by age
     *
     * @param age
     * @return Collection of Student
     * @throws NotFoundException
     */
    Collection<Student> filterByAge(int age) throws NotFoundException;

    /**
     * Filter student by age between
     *
     * @param ageMin
     * @param ageMax
     * @return Collection of Student
     * @throws NotFoundException
     */
    Collection<Student> filterByAgeBetween(int ageMin, int ageMax) throws NotFoundException;
}
