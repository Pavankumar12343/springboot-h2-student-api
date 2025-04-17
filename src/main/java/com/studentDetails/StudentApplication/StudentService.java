package com.studentDetails.StudentApplication;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService {
	private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private StudentRepository repository;
    
    private String calculateGrade(Student student) {
    	logger.info("Calculating grade of a student");
        int marks = student.getMarksObtained();
        LocalDate admissionDate = student.getAdmissionDate();
        boolean isRecent = admissionDate != null && admissionDate.isAfter(LocalDate.now().minusMonths(6));

        if (marks >= 90 && isRecent) return "Platinum";
        if (marks >= 80 && marks < 90 && isRecent) return "Merit";
        if (marks > 40) return "Pass";
        return "Fail";
    }


    public Student createStudent(Student student) {
    	logger.info("Creating student details");
        return repository.save(student);
    }

    public Optional<Student> getStudentById(UUID id) {
    	logger.info("Fetching student with ID: {}", id);
        return repository.findById(id);
    }
    public List<Student> getStudentsByName(String name) {
    	logger.info("Fetching student with name: {}", name);
        return repository.findByName(name);
    }

    public Optional<Student> getStudentByEmail(String email) {
    	logger.info("Fetching student with email: {}", email);
        return repository.findByEmail(email);
    }

    public Student updateStudent(UUID id, Student student) {
    	logger.info("Updating student details");
        student.setName(student.getName());
        student.setEmail(student.getEmail());

        return repository.save(student);
    }

    public void deleteStudent(UUID id) {
    	logger.info("Fetching student with ID: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found with id " + id);
        }
        repository.deleteById(id);
    }
}

