package org.tyutyunik.school.service.impl;

import org.springframework.stereotype.Service;
import org.tyutyunik.school.exceptions.AlreadyAddedException;
import org.tyutyunik.school.exceptions.IsNotValid;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Faculty;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.repository.FacultyRepository;
import org.tyutyunik.school.service.FacultyService;

import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(Long id) throws IsNotValid, NotFoundException {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
    }

    @Override
    public Faculty update(Long id, Faculty faculty) throws AlreadyAddedException, IsNotValid, NotFoundException {
        // todo (v2) the lighter implementation of update
        /*return facultyRepository.findById(id)
                .map(facultyForUpdate -> {
                    facultyRepository.save(faculty);
                    return faculty;
                })
                .orElseThrow(() -> new NotFoundException(id));*/
        // todo (v1) can be replaced with the lighter implementation of update
        if (!facultyRepository.existsById(id)) {
            throw new NotFoundException(this.getClass(), id);
        }
        faculty.setId(id);
        facultyRepository.save(faculty);
        return faculty;
    }

    @Override
    public Faculty delete(Long id) throws IsNotValid, NotFoundException {
        // todo (v2) the lighter implementation of delete
        /*return facultyRepository.findById(id)
                .map(facultyForUpdate -> {
                    facultyRepository.deleteById(id);
                    return faculty;
                })
                .orElseThrow(() -> new NotFoundException(id));*/
        // todo (v1) can be replaced with the lighter implementation of delete
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
        facultyRepository.deleteById(faculty.getId());
        return faculty;
    }

    @Override
    public Collection<Faculty> readAll() {
        return facultyRepository.findAll()
                .stream()
                .toList();
    }

    public Collection<Faculty> filterByColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .toList();
    }
}
