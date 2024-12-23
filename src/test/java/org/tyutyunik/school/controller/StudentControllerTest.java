package org.tyutyunik.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tyutyunik.school.model.Student;
import org.tyutyunik.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StudentRepository studentRepository;

    private String urlConfiguration(String path) {
        return "http://localhost:" + port + "/student" + path;
    }

    @Test
    void create() {
        Student student = new Student("Iskander", 20);

        ResponseEntity<Student> response = testRestTemplate.postForEntity(
                urlConfiguration("/create"),
                student,
                Student.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(student.getName());
        assertThat(responseBody.getAge()).isEqualTo(student.getAge());

        Student actual = studentRepository.findById(responseBody.getId()).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(responseBody.getName());
        assertThat(actual.getAge()).isEqualTo(responseBody.getAge());
    }

    @Test
    void read() {
        Student student = new Student("Iskander", 20);

        studentRepository.save(student);
        ResponseEntity<Student> response = testRestTemplate.getForEntity(
                urlConfiguration("/read/{id}"),
                Student.class,
                student.getId());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(student.getName());
        assertThat(responseBody.getAge()).isEqualTo(student.getAge());
    }

    @Test
    void readAll() {
        Student student1 = new Student("Iskander", 15);
        Student student2 = new Student("Alexandr", 20);
        Student student3 = new Student("Alex", 25);

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        ResponseEntity<Student> response1 = testRestTemplate.getForEntity(
                urlConfiguration("/read/1"),
                Student.class,
                student1.getId());
        assertThat(response1).isNotNull();
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student response1Body = response1.getBody();
        assertThat(response1Body).isNotNull();
        assertThat(response1Body.getId()).isNotNull();
        assertThat(response1Body.getName()).isEqualTo(student1.getName());
        assertThat(response1Body.getAge()).isEqualTo(student1.getAge());

        ResponseEntity<Student> response2 = testRestTemplate.getForEntity(
                urlConfiguration("/read/2"),
                Student.class,
                student2.getId());
        assertThat(response2).isNotNull();
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student response2Body = response2.getBody();
        assertThat(response2Body).isNotNull();
        assertThat(response2Body.getId()).isNotNull();
        assertThat(response2Body.getName()).isEqualTo(student2.getName());
        assertThat(response2Body.getAge()).isEqualTo(student2.getAge());

        ResponseEntity<Student> response3 = testRestTemplate.getForEntity(
                urlConfiguration("/read/3"),
                Student.class,
                student3.getId());
        assertThat(response3).isNotNull();
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student response3Body = response3.getBody();
        assertThat(response3Body).isNotNull();
        assertThat(response3Body.getId()).isNotNull();
        assertThat(response3Body.getName()).isEqualTo(student3.getName());
        assertThat(response3Body.getAge()).isEqualTo(student3.getAge());
    }

    @Test
    void update() {
        Student student = new Student("Iskander", 20);
        Student studentNew = new Student("Alexandr", 25);

        studentRepository.save(student);
        ResponseEntity<Student> response = testRestTemplate.exchange(
                urlConfiguration("/update/{id}"),
                HttpMethod.PUT,
                new HttpEntity<>(studentNew),
                Student.class,
                student.getId());

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(studentNew.getName());
        assertThat(responseBody.getAge()).isEqualTo(studentNew.getAge());

        Student actual = studentRepository.findById(responseBody.getId()).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(studentNew.getName());
        assertThat(actual.getAge()).isEqualTo(studentNew.getAge());
    }

    @Test
    void deleteById() {
        Student student = new Student("Iskander", 20);

        studentRepository.save(student);
        ResponseEntity<Student> response = testRestTemplate.exchange(
                urlConfiguration("/delete/{id}"),
                HttpMethod.DELETE,
                null,
                Student.class,
                student.getId()
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(student.getName());
        assertThat(responseBody.getAge()).isEqualTo(student.getAge());

        Student actual = studentRepository.findById(responseBody.getId()).orElse(null);
        assertThat(actual).isNull();
    }

    @Test
    void filterByAge() throws JsonProcessingException {
        Student student1 = new Student("Iskander", 15);
        Student student2 = new Student("Alexandr", 20);
        Student student3 = new Student("Alex", 25);
        int age = 20;

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                urlConfiguration("/filterByAge?age=" + age),
                String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Student> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Collection<Student>>() {
        });
        assertThat(responseObject).hasSize(1);
        assertThat(responseObject.iterator().next().getName()).isEqualTo("Alexandr");
        assertThat(responseObject.iterator().next().getAge()).isEqualTo(20);
    }

    @Test
    void filterByAgeBetween() throws JsonProcessingException {
        Student student1 = new Student("Iskander", 15);
        Student student2 = new Student("Alexandr", 20);
        Student student3 = new Student("Alex", 25);
        int minAge = 20;
        int maxAge = 25;

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                urlConfiguration("/filterByAgeBetween?minAge=" + minAge + "&maxAge=" + maxAge),
                String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Student> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Collection<Student>>() {
        });
        System.out.println("responseObject = " + responseObject);
        assertThat(responseObject).hasSize(2);

        Iterator<Student> iterator = responseObject.iterator();
        Student responseStudent1 = iterator.next();
        Student responseStudent2 = iterator.next();
        assertThat(responseStudent1.getName()).isEqualTo("Alexandr");
        assertThat(responseStudent1.getAge()).isEqualTo(20);
        assertThat(responseStudent2.getName()).isEqualTo("Alex");
        assertThat(responseStudent2.getAge()).isEqualTo(25);
    }
}