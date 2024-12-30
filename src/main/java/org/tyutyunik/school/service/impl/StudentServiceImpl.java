package org.tyutyunik.school.service.impl;

import org.springframework.stereotype.Service;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.repository.StudentRepository;
import org.tyutyunik.school.service.StudentService;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
    }

    @Override
    public Collection<Student> readAll() {
        return studentRepository.findAll()
                .stream()
                .toList();
    }

    @Override
    public Collection<Student> readLast5() {
        return studentRepository.readLast5();
    }

    @Override
    public Student update(Long id, Student student) {
        // todo (v2) the lighter implementation of update
        /*return studentRepository.findById(id)
                .map(studentForUpdate -> {
                    studentRepository.save(student);
                    return student;
                })
                .orElseThrow(() -> new NotFoundException(id));*/
        // todo (v1) can be replaced with the lighter implementation of update
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException(this.getClass(), id);
        }
        student.setId(id);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student delete(Long id) {
        // todo (v2) the lighter implementation of delete
        /*return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.deleteById(id);
                    return student;
                })
                .orElseThrow(() -> new NotFoundException(id));*/
        // todo (v1) can be replaced with the lighter implementation of delete
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
        studentRepository.deleteById(student.getId());
        return student;
    }

    @Override
    public Long countAll() {
        return studentRepository.countAll();
    }

    @Override
    public Long ageAvg() {
        return studentRepository.ageAvg();
    }

    @Override
    public Collection<Student> filterByAge(int age) {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

    @Override
    public Collection<Student> filterByAgeBetween(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin, ageMax);
        // v1
        /*return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() >= ageMin && student.getAge() <= ageMax)
                .toList();*/
    }
}
