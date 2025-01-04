package org.tyutyunik.school.controller;

import org.springframework.web.bind.annotation.*;
import org.tyutyunik.school.model.Faculty;
import org.tyutyunik.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/create")
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("/read/{id}")
    public Faculty read(@PathVariable("id") Long id) {
        return facultyService.read(id);
    }

    @GetMapping("/read/all")
    public Collection<Faculty> readAll() {
        return facultyService.readAll();
    }

    @PutMapping("/update/{id}")
    public Faculty update(@PathVariable("id") Long id,
                          @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/delete/{id}")
    public Faculty deleteById(@PathVariable("id") Long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/filterByColor")
    public Collection<Faculty> filterByAge(@RequestParam("color") String color) {
        return facultyService.filterByColor(color);
    }

    @GetMapping("/filterByName")
    public Collection<Faculty> filterByName(@RequestParam("name") String name) {
        return facultyService.filterByName(name);
    }

    @GetMapping("/filterByNameLongest")
    public Faculty filterByNameLongest() {
        return facultyService.filterByNameLongest();
    }
}
