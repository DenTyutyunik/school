package org.tyutyunik.school.controller;

import org.springframework.web.bind.annotation.*;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.service.StudentService;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/read/{id}")
    public Student read(@PathVariable("id") Long id) {
        return studentService.read(id);
    }

    @GetMapping("/read/all")
    public Collection<Student> readAll() {
        return studentService.readAll();
    }

    @PutMapping("/update/{id}")
    public Student update(@PathVariable("id") Long id,
                          @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/delete/{id}")
    public Student deleteById(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }

    @GetMapping("/filterByAge")
    public Collection<Student> filterByAge(@RequestParam("age") int age) {
        return studentService.filterByAge(age);
    }

    @GetMapping("/filterByAgeBetween")
    public Collection<Student> filterByAgeBetween(@RequestParam("minAge") int minAge,
                                                  @RequestParam("maxAge") int maxAge) {
        return studentService.filterByAgeBetween(minAge, maxAge);
    }
}
