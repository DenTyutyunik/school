package org.tyutyunik.school.controller;

import org.springframework.web.bind.annotation.*;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.service.StudentService;

import java.util.Collection;

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

    @GetMapping("/read/last5")
    public Collection<Student> readLast5() {
        return studentService.readLast5();
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

    @GetMapping("/count/all")
    public Long countAll() {
        return studentService.countAll();
    }

    @GetMapping("/age/avg")
    public Long ageAvg() {
        return studentService.ageAvg();
    }

    @GetMapping("/age/avg2")
    public Long ageAvgStream() {
        return studentService.ageAvg2();
    }

    @GetMapping("/filterByNameAlphabeticalOrderStartingWithA")
    public Collection<Student> filterByNameAlphabeticalOrderStartingWithA() {
        return studentService.filterByNameAlphabeticalOrderStartingWithA();
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

    @GetMapping("/doesNotMatter")
    public Integer doesNotMatter() {
        return studentService.doesNotMatter();
    }

    @GetMapping("/doesNotMatter2")
    public Integer doesNotMatter2() {
        return studentService.doesNotMatter2();
    }

    @GetMapping("/read/print-parallel")
    public Collection<Student> readAllParallel() {
        return studentService.readAllParallel();
    }

    @GetMapping("/read/print-synchronized")
    public Collection<Student> readAllSynchronized() {
        return studentService.readAllSynchronized();
    }
}
