package org.tyutyunik.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tyutyunik.school.exceptions.AlreadyAddedException;
import org.tyutyunik.school.exceptions.IsNotValidException;
import org.tyutyunik.school.exceptions.NotFoundException;
import org.tyutyunik.school.model.Faculty;
import org.tyutyunik.school.repository.FacultyRepository;
import org.tyutyunik.school.service.FacultyService;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("[INFO] [FacultyService] Was invoked create()");
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
        logger.info("[INFO] [FacultyService] Was invoked read()");
        return facultyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(this.getClass(), id));
    }

    @Override
    public Faculty update(Long id, Faculty faculty) throws AlreadyAddedException, IsNotValidException, NotFoundException {
        logger.info("[INFO] [FacultyService] Was invoked update()");
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
        logger.info("[INFO] [FacultyService] Was invoked delete()");
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
        logger.info("[INFO] [FacultyService] Was invoked readAll()");
        return facultyRepository.findAll()
                .stream()
                .toList();
    }

    public Collection<Faculty> filterByColor(String color) {
        logger.info("[INFO] [FacultyService] Was invoked filterByColor()");
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equalsIgnoreCase(color))
                .toList();
    }

    public Collection<Faculty> filterByName(String name) {
        logger.info("[INFO] [FacultyService] Was invoked filterByName()");
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getName().equalsIgnoreCase(name))
                .toList();
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
