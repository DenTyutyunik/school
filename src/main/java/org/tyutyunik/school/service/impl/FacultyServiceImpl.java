package org.tyutyunik.school.service.impl;

import org.springframework.stereotype.Service;
import org.tyutyunik.school.exceptions.AlreadyAddedException;
import org.tyutyunik.school.exceptions.IsNotValidException;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Faculty;
import org.tyutyunik.school.repository.FacultyRepository;
import org.tyutyunik.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        if (faculty.getName().isEmpty()) {
            throw new IsNotValidException(FacultyService.class, faculty.getId(), "Name must not be null");
        }
        if (faculty.getColor().isEmpty()) {
            throw new IsNotValidException(FacultyService.class, faculty.getId(), "Color must not be null");
        }
        if (findByName(faculty.getName())) {
            throw new IsNotValidException(FacultyService.class, faculty.getId(), "Name must be unique");
        }
        if (findByColor(faculty.getColor())) {
            throw new IsNotValidException(FacultyService.class, faculty.getId(), "Name must be unique");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(Long id) throws IsNotValidException, NotFoundException {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
    }

    @Override
    public Faculty update(Long id, Faculty faculty) throws AlreadyAddedException, IsNotValidException, NotFoundException {
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
    public Faculty delete(Long id) throws IsNotValidException, NotFoundException {
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
                .filter(faculty -> faculty.getColor().equalsIgnoreCase(color))
                .toList();
    }

    public Collection<Faculty> filterByName(String name) {
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getName().equalsIgnoreCase(name))
                .toList();
    }

    @Override
    public Faculty filterByNameLongest() {
        return facultyRepository.findAll()
                .stream()
                .parallel()
                .max(Comparator.comparingInt(faculty -> faculty.getName().length()))
                .orElse(null);
    }

    private Boolean findByName(String name) {
        return facultyRepository.findAll()
                .stream()
                .anyMatch(faculty -> faculty.getName().equalsIgnoreCase(name));
    }

    private Boolean findByColor(String name) {
        return facultyRepository.findAll()
                .stream()
                .anyMatch(faculty -> faculty.getColor().equalsIgnoreCase(name));
    }
}
