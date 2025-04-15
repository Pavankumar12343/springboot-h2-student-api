package com.studentDetails.StudentApplication;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, UUID> {
	List<Student> findByName(String name);
    Optional<Student> findByEmail(String email);
}

