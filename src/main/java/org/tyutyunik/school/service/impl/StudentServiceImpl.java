package org.tyutyunik.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tyutyunik.school.exceptions.IsNotValidException;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.repository.StudentRepository;
import org.tyutyunik.school.service.StudentService;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student create(Student student) {
        logger.info("[INFO] [StudentService] Was invoked create()");
        if (student.getAge() == 0) {
            logger.warn("[WARN] [StudentService] Age is set by default");
            student.setAge(20);
        }
        if (student.getAge() < 16) {
            throw new IsNotValidException(StudentService.class, student.getId(), "Age must not be less than 16");
        }
        if (student.getName().isEmpty()) {
            throw new IsNotValidException(StudentService.class, student.getId(), "Name must not be null");
        }
        if (findByName(student.getName())) {
            throw new IsNotValidException(StudentService.class, student.getId(), "Name must be unique");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {
        logger.info("[INFO] [StudentService] Was invoked student.read()");
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
    }

    @Override
    public Collection<Student> readAll() {
        logger.info("[INFO] [StudentService] Was invoked student.readAll()");
        return studentRepository.findAll()
                .stream()
                .toList();
    }

    @Override
    public Collection<Student> readLast5() {
        logger.info("[INFO] [StudentService] Was invoked student.readLast5()");
        return studentRepository.readLast5();
    }

    @Override
    public Student update(Long id, Student student) {
        logger.info("[INFO] [StudentService] Was invoked update()");
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
        logger.info("[INFO] [StudentService] Was invoked delete()");
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
        logger.info("[INFO] [StudentService] Was invoked countAll()");
        return studentRepository.countAll();
    }

    @Override
    public Long ageAvg() {
        logger.info("[INFO] [StudentService] Was invoked ageAvg()");
        return studentRepository.ageAvg();
    }

    @Override
    public Long ageAvg2() {
        return (long) studentRepository.findAll()
                .stream()
                .parallel()
                .filter(student -> student.getAge() > 0)
                .mapToLong(Student::getAge)
                .average()
                .orElse(0);
    }

    @Override
    public Collection<Student> filterByNameAlphabeticalOrderStartingWithA() {
        return studentRepository.findAll()
                .stream()
                .parallel()
                .filter(student -> student.getName().startsWith("A"))
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }

    @Override
    public Collection<Student> filterByAge(int age) {
        logger.info("[INFO] [StudentService] Was invoked filterByAge()");
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }

    @Override
    public Collection<Student> filterByAgeBetween(int ageMin, int ageMax) {
        logger.info("[INFO] [StudentService] Was invoked filterByAgeBetween()");
        return studentRepository.findByAgeBetween(ageMin, ageMax);
        // v1
        /*return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() >= ageMin && student.getAge() <= ageMax)
                .toList();*/
    }

    @Override
    public Integer doesNotMatter() {
        return Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }

    @Override
    public Integer doesNotMatter2() {
        return IntStream
                .rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

    private Boolean findByName(String name) {
        return studentRepository.findAll()
                .stream()
                .anyMatch(student -> student.getName().equalsIgnoreCase(name));
    }
}
