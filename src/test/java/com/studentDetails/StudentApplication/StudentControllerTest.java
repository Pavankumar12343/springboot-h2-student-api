package com.studentDetails.StudentApplication;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(StudentControllerTest.class);
    @Mock
    private StudentService studentService;
    
    @Mock
    private StudentRepository studentRepository;
    

    private MockMvc mockMvc;
    
    @InjectMocks
    private StudentController studentController;

    private Student student;

    @BeforeEach
    void setup() {
    	mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        student = new Student();
        student.setId(UUID.randomUUID());
        student.setName("John Doe");
        student.setEmail("john@example.com");
        student.setMarksObtained(85);
        student.setAdmissionDate(LocalDate.parse("2023-09-01"));
    }
    
    @Test
    public void testCreateStudent() {
    	logger.info("StudentControllerTest::testCreateStudent method");
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        ResponseEntity<Student> response = studentController.createStudent(student);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testGetStudent() {
    	logger.info("StudentControllerTest::testGetStudent method");
        UUID id = student.getId();
        when(studentService.getStudentById(id)).thenReturn(Optional.of(student));

        ResponseEntity<Optional<Student>> response = studentController.getStudent(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().get().getName());
    }
    @Test
    public void testGradeCalculation_Merit() throws Exception {
    	logger.info("StudentControllerTest::testGradeCalculation_Merit method");
        Student student = new Student();
        student.setName("Jane");
        student.setEmail("jane@example.com");
        student.setMarksObtained(85);
        student.setAdmissionDate(LocalDate.now().minusMonths(3));
        studentRepository.save(student);

        mockMvc.perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value("Merit"));
    }

    @Test
    public void testGradeCalculation_Platinum() throws Exception {
    	logger.info("StudentControllerTest::testGradeCalculation_Platinum method");
        Student student = new Student();
        student.setName("Alex");
        student.setEmail("alex@example.com");
        student.setMarksObtained(91);
        student.setAdmissionDate(LocalDate.now().minusMonths(1));
        studentRepository.save(student);

        mockMvc.perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value("Platinum"));
    }

    @Test
    public void testGradeCalculation_Pass() throws Exception {
    	logger.info("StudentControllerTest::testGradeCalculation_Pass method");
        Student student = new Student();
        student.setName("Ben");
        student.setEmail("ben@example.com");
        student.setMarksObtained(60);
        student.setAdmissionDate(LocalDate.now().minusYears(1));
        studentRepository.save(student);

        mockMvc.perform(get("/students/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value("Pass"));
    }

    @Test
    public void testEmailValidation() throws Exception {
    	logger.info("StudentControllerTest::testEmailValidation method");
        String studentJson = """
            {
                "name": "Invalid Email",
                "email": "invalid-email",
                "marksObtained": 75,
                "admissionDate": "%s"
            }
            """.formatted(LocalDate.now().toString());

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteStudent() throws Exception {
    	logger.info("StudentControllerTest::testDeleteStudent method");
        Student student = new Student();
        student.setName("ToDelete");
        student.setEmail("delete@example.com");
        student.setMarksObtained(50);
        student.setAdmissionDate(LocalDate.now());
        studentRepository.save(student);

        mockMvc.perform(delete("/students/" + student.getId()))
                .andExpect(status().isNoContent());
    }
}

