package org.tyutyunik.school.service.impl;

import org.springframework.stereotype.Service;
import org.tyutyunik.school.exceptions.AlreadyAddedException;
import org.tyutyunik.school.exceptions.IsNotValid;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Faculty;
import org.tyutyunik.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;

import static java.util.Collections.unmodifiableCollection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final HashMap<Long, Faculty> storageFaculty = new HashMap<>();
    private long idCount = 0;

    @Override
    public Long create(Faculty faculty) {
        faculty.setId(++idCount);
        storageFaculty.put(idCount, faculty);
        return idCount;
    }

    @Override
    public Faculty read(Long id) throws IsNotValid, NotFoundException {
        checkFacultyExist(id);
        return storageFaculty.get(id);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) throws AlreadyAddedException, IsNotValid, NotFoundException {
        checkFacultyExist(id);
        return storageFaculty.put(id, faculty);
    }

    @Override
    public Faculty delete(Long id) throws IsNotValid, NotFoundException {
        checkFacultyExist(id);
        return storageFaculty.remove(id);
    }

    @Override
    public Collection<Faculty> readAll() {
        return unmodifiableCollection(storageFaculty.values());
    }

    @Override
    public HashMap<Long, Faculty> filterByColor(String color) {
        return null;
    }

    private void checkFacultyExist(long id) {
        if (!storageFaculty.containsKey(id)) {
            throw new NotFoundException();
        }
    }
}
