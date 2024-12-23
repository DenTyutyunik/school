package org.tyutyunik.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.repository.StudentRepository;
import org.tyutyunik.school.service.impl.StudentServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWithWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private StudentServiceImpl studentService;
    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        Student student = new Student("Iskander", 20);
        String studentBody = objectMapper.writeValueAsString(student);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student/create")
                        .content(studentBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("Iskander"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void read() throws Exception {
        Student student = new Student("Iskander", 20);
        Long id = 1L;
        student.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/read/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value("Iskander"))
                .andExpect(jsonPath("$.age").value(20));
        ;
    }

    @Test
    void readAll() {
    }

    @Test
    void update() throws Exception {
        Student student = new Student("Iskander", 20);
        Long id = 1L;
        student.setId(id);
        when(studentRepository.existsById(id)).thenReturn(true);

        Student studentNew = new Student("Alexandr", 25);
        studentNew.setId(id);
        String studentNewString = objectMapper.writeValueAsString(studentNew);
        when(studentRepository.save(any(Student.class))).thenReturn(studentNew);

        mockMvc.perform(put("/student/update/" + student.getId())
                        .content(studentNewString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alexandr"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void deleteById() throws Exception {
        Student student = new Student("Iskander", 20);
        Long id = 1L;

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        mockMvc.perform(delete("/student/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.age").exists());
    }


    @Test
    void filterByAge() throws Exception {
        Student student1 = new Student("Iskander", 20);
        Student student2 = new Student("Alexandr", 25);
        Student student3 = new Student("Alex", 30);
        Collection<Student> expectedStudents = Arrays.asList(student1);

        when(studentService.filterByAge(20)).thenReturn(expectedStudents);

        // Perform the GET request
        mockMvc.perform(get("/student/filterByAge")
                        .param("age", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Iskander"));
    }

    @Test
    void filterByAgeBetween() throws Exception {
        Student student1 = new Student("Iskander", 20);
        Student student2 = new Student("Alexandr", 25);
        Student student3 = new Student("Alex", 30);
        Collection<Student> expectedStudents = Arrays.asList(student1, student2);

        when(studentService.filterByAgeBetween(18, 27)).thenReturn(expectedStudents);

        // Perform the GET request
        mockMvc.perform(get("/student/filterByAgeBetween")
                        .param("minAge", "18")
                        .param("maxAge", "27")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Iskander"))
                .andExpect(jsonPath("$[1].name").value("Alexandr"));
    }
}
