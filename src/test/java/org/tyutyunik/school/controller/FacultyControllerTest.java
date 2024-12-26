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
import org.tyutyunik.school.model.Faculty;
import org.tyutyunik.school.repository.FacultyRepository;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private FacultyRepository facultyRepository;

    private String urlConfiguration(String path) {
        return "http://localhost:" + port + "/faculty" + path;
    }

    @Test
    void create() {
        Faculty faculty = new Faculty("Fakulte", "Red");

        ResponseEntity<Faculty> response = testRestTemplate.postForEntity(
                urlConfiguration("/create"),
                faculty,
                Faculty.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(faculty.getName());
        assertThat(responseBody.getColor()).isEqualTo(faculty.getColor());

        Faculty actual = facultyRepository.findById(responseBody.getId()).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(responseBody.getName());
        assertThat(actual.getColor()).isEqualTo(responseBody.getColor());
    }

    @Test
    void read() {
        Faculty faculty = new Faculty("Fakulte", "Red");

        facultyRepository.save(faculty);
        ResponseEntity<Faculty> response = testRestTemplate.getForEntity(
                urlConfiguration("/read/{id}"),
                Faculty.class,
                faculty.getId());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(faculty.getName());
        assertThat(responseBody.getColor()).isEqualTo(faculty.getColor());
    }

    @Test
    void readAll() {
        Faculty faculty1 = new Faculty("Fakulte", "Red");
        Faculty faculty2 = new Faculty("Fakultet", "Yellow");
        Faculty faculty3 = new Faculty("Faculty", "Blue");

        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);
        facultyRepository.save(faculty3);

        ResponseEntity<Faculty> response1 = testRestTemplate.getForEntity(
                urlConfiguration("/read/1"),
                Faculty.class,
                faculty1.getId());
        assertThat(response1).isNotNull();
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty response1Body = response1.getBody();
        assertThat(response1Body).isNotNull();
        assertThat(response1Body.getId()).isNotNull();
        assertThat(response1Body.getName()).isEqualTo(faculty1.getName());
        assertThat(response1Body.getColor()).isEqualTo(faculty1.getColor());

        ResponseEntity<Faculty> response2 = testRestTemplate.getForEntity(
                urlConfiguration("/read/2"),
                Faculty.class,
                faculty2.getId());
        assertThat(response2).isNotNull();
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty response2Body = response2.getBody();
        assertThat(response2Body).isNotNull();
        assertThat(response2Body.getId()).isNotNull();
        assertThat(response2Body.getName()).isEqualTo(faculty2.getName());
        assertThat(response2Body.getColor()).isEqualTo(faculty2.getColor());

        ResponseEntity<Faculty> response3 = testRestTemplate.getForEntity(
                urlConfiguration("/read/3"),
                Faculty.class,
                faculty3.getId());
        assertThat(response3).isNotNull();
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty response3Body = response3.getBody();
        assertThat(response3Body).isNotNull();
        assertThat(response3Body.getId()).isNotNull();
        assertThat(response3Body.getName()).isEqualTo(faculty3.getName());
        assertThat(response3Body.getColor()).isEqualTo(faculty3.getColor());
    }

    @Test
    void update() {
        Faculty faculty = new Faculty("Fakulte", "Red");
        Faculty facultyNew = new Faculty("Faculty", "Blue");

        facultyRepository.save(faculty);
        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                urlConfiguration("/update/{id}"),
                HttpMethod.PUT,
                new HttpEntity<>(facultyNew),
                Faculty.class,
                faculty.getId());

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(facultyNew.getName());
        assertThat(responseBody.getColor()).isEqualTo(facultyNew.getColor());

        Faculty actual = facultyRepository.findById(responseBody.getId()).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(facultyNew.getName());
        assertThat(actual.getColor()).isEqualTo(facultyNew.getColor());
    }

    @Test
    void deleteById() {
        Faculty faculty = new Faculty("Fakulte", "Red");

        facultyRepository.save(faculty);
        ResponseEntity<Faculty> response = testRestTemplate.exchange(
                urlConfiguration("/delete/{id}"),
                HttpMethod.DELETE,
                null,
                Faculty.class,
                faculty.getId()
        );

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getId()).isNotNull();
        assertThat(responseBody.getName()).isEqualTo(faculty.getName());
        assertThat(responseBody.getColor()).isEqualTo(faculty.getColor());

        Faculty actual = facultyRepository.findById(responseBody.getId()).orElse(null);
        assertThat(actual).isNull();
    }

    @Test
    void filterByColor() throws JsonProcessingException {
        Faculty faculty1 = new Faculty("Fakulte", "Red");
        Faculty faculty2 = new Faculty("Fakultet", "Yellow");
        Faculty faculty3 = new Faculty("Faculty", "Blue");
        String color = "Yellow";

        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);
        facultyRepository.save(faculty3);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                urlConfiguration("/filterByColor?color=" + color),
                String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Faculty> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Collection<Faculty>>() {
        });
        assertThat(responseObject).hasSize(1);
        assertThat(responseObject.iterator().next().getName()).isEqualTo("Fakultet");
        assertThat(responseObject.iterator().next().getColor()).isEqualTo("Yellow");
    }

    @Test
    void filterByName() throws JsonProcessingException {
        Faculty faculty1 = new Faculty("Fakulte", "Red");
        Faculty faculty2 = new Faculty("Fakultet", "Yellow");
        Faculty faculty3 = new Faculty("Faculty", "Blue");
        String name = "Fakultet";

        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);
        facultyRepository.save(faculty3);

        ResponseEntity<String> response = testRestTemplate.getForEntity(
                urlConfiguration("/filterByName?name=" + name),
                String.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ObjectMapper objectMapper = new ObjectMapper();
        Collection<Faculty> responseObject = objectMapper.readValue(response.getBody(), new TypeReference<Collection<Faculty>>() {
        });
        assertThat(responseObject).hasSize(1);
        assertThat(responseObject.iterator().next().getName()).isEqualTo("Fakultet");
        assertThat(responseObject.iterator().next().getColor()).isEqualTo("Yellow");
    }
}
