package com.studentDetails.StudentApplication;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;
    
    private String calculateGrade(Student student) {
        int marks = student.getMarksObtained();
        LocalDate admissionDate = student.getAdmissionDate();
        boolean isRecent = admissionDate != null && admissionDate.isAfter(LocalDate.now().minusMonths(6));

        if (marks >= 90 && isRecent) return "Platinum";
        if (marks >= 80 && marks < 90 && isRecent) return "Merit";
        if (marks > 40) return "Pass";
        return "Fail";
    }


    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Optional<Student> getStudentById(UUID id) {
        return repository.findById(id);
    }
    public List<Student> getStudentsByName(String name) {
        return repository.findByName(name);
    }

    public Optional<Student> getStudentByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Student updateStudent(UUID id, Student student) {

        student.setName(student.getName());
        student.setEmail(student.getEmail());

        return repository.save(student);
    }

    public void deleteStudent(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Student not found with id " + id);
        }
        repository.deleteById(id);
    }
}

