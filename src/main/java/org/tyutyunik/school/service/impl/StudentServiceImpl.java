package org.tyutyunik.school.service.impl;

import org.springframework.stereotype.Service;
import org.tyutyunik.school.exceptions.AlreadyAddedException;
import org.tyutyunik.school.exceptions.IsNotValid;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;

import static java.util.Collections.unmodifiableCollection;

@Service
public class StudentServiceImpl implements StudentService {
    private final HashMap<Long, Student> storageStudent = new HashMap<>();
    private long idCount = 0;

    @Override
    public Long create(Student student) {
        student.setId(++idCount);
        storageStudent.put(idCount, student);
        return idCount;
    }

    @Override
    public Student read(Long id) {
        return storageStudent.get(id);
    }

    @Override
    public Student update(Long id, Student student) throws AlreadyAddedException, IsNotValid, NotFoundException {
        checkStudentExist(id);
        return storageStudent.put(id, student);
    }

    @Override
    public Student delete(Long id) {
        checkStudentExist(id);
        return storageStudent.remove(id);
    }

    @Override
    public Collection<Student> readAll() {
        return unmodifiableCollection(storageStudent.values());
    }

    @Override
    public HashMap<Long, Student> filterByAge(int age) {
        return null;
    }

    private void checkStudentExist(long id) {
        if (!storageStudent.containsKey(id)) {
            throw new NotFoundException();
        }
    }
}
