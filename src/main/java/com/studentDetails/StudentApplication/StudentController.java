package com.studentDetails.StudentApplication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/students")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
    	logger.info("StudentController::creating student details");
        Student savedStudent = studentService.createStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable UUID id) {
    	logger.info("StudentController::Fetching student with ID: {}", id);
    	Optional<Student> student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    @GetMapping
    public ResponseEntity<?> getStudentByNameOrEmail(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String email) {
    	logger.info("StudentController::Fetching student with name or email: {},{}", name,email);
        if (name != null) {
            List<Student> students = studentService.getStudentsByName(name);
            return ResponseEntity.ok(students);
        } else if (email != null) {
            Optional<Student> student = studentService.getStudentByEmail(email);
            return student.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.badRequest().body("Please provide name or email");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable UUID id, @RequestBody Student student) {
    	logger.info("StudentController::updating student details");
        try {
            Student updated = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable UUID id) {
    	logger.info("StudentController::deleting student details by id: {}",id);
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student deleted successfully.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

