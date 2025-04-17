package com.studentDetails.StudentApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceTest.class);
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setup() {
        student = new Student();
        student.setId(UUID.randomUUID());
        student.setName("Test Student");
        student.setEmail("test@example.com");
        student.setMarksObtained(90);
        student.setAdmissionDate(LocalDate.parse("2023-09-01"));
    }

    @Test
    public void testCreateStudent() {
    	logger.info("StudentServiceTest::testCreateStudent method");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student savedStudent = studentService.createStudent(student);

        assertNotNull(savedStudent);
        assertEquals("Test Student", savedStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testGetStudentById_whenFound() {
    	logger.info("StudentServiceTest::testGetStudentById_whenFound method");
        UUID id = student.getId();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.getStudentById(id);

        assertNotNull(result);
        assertEquals("Test Student", result.get().getName());
    }

    @Test
    public void testGetStudentById_whenNotFound() {
    	logger.info("StudentServiceTest::testGetStudentById_whenNotFound method");
        UUID id = UUID.randomUUID();
        when(studentRepository.findById(id)).thenReturn(null);

        assertThrows(Exception.class, () -> studentService.getStudentById(id));
    }
}

