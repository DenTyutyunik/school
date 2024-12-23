package org.tyutyunik.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.tyutyunik.school.model.Faculty;
import org.tyutyunik.school.repository.FacultyRepository;
import org.tyutyunik.school.service.impl.FacultyServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerWithWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private FacultyServiceImpl facultyService;
    @MockBean
    private FacultyRepository facultyRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create() throws Exception {
        Faculty faculty = new Faculty("Fakulte", "Navy blue");
        String facultyBody = objectMapper.writeValueAsString(faculty);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty/create")
                        .content(facultyBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.color").exists());
    }

    @Test
    void read() throws Exception {
        Faculty faculty = new Faculty("Fakulte", "Navy blue");
        Long id = 1L;
        faculty.setId(id);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/read/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.color").exists());
    }

    @Test
    void readAll() {
    }

    @Test
    void update() throws Exception {
        Faculty faculty = new Faculty("Fakulte", "Navy blue");
        Long id = 1L;
        faculty.setId(id);
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        Faculty facultyNew = new Faculty("Faculty", "Deep blue");
        facultyNew.setId(id);
        String facultyNewString = objectMapper.writeValueAsString(facultyNew);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(facultyNew);

        mockMvc.perform(put("/faculty/update/" + faculty.getId())
                        .content(facultyNewString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Faculty"))
                .andExpect(jsonPath("$.color").value("Deep blue"));
    }

    @Test
    void deleteById() throws Exception {
        Faculty faculty = new Faculty("Fakulte", "Navy blue");
        Long id = 1L;

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        doNothing().when(facultyRepository).delete(faculty);

        mockMvc.perform(delete("/faculty/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.color").exists());
    }

    @Test
    void filterByName() throws Exception {
        Faculty facultyFirst = new Faculty("Fakulte", "Navy blue");
        Faculty facultySecond = new Faculty("Faculty", "Deep blue");
        Collection<Faculty> expectedFaculty = Arrays.asList(facultyFirst);

        when(facultyService.filterByName("Fakulte")).thenReturn(expectedFaculty);

        // Perform the GET request
        mockMvc.perform(get("/faculty/filterByName")
                        .param("name", "Fakulte")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Fakulte"));
    }

    @Test
    void filterByColor() throws Exception {
        Faculty facultyFirst = new Faculty("Fakulte", "Navy blue");
        Faculty facultySecond = new Faculty("Faculty", "Deep blue");
        Collection<Faculty> expectedFaculty = Arrays.asList(facultyFirst);

        when(facultyService.filterByColor("Navy blue")).thenReturn(expectedFaculty);

        // Perform the GET request
        mockMvc.perform(get("/faculty/filterByColor")
                        .param("color", "Navy blue")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].color").value("Navy blue"));
    }
}