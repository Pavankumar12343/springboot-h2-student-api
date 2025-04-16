package com.studentDetails.StudentApplication;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Student {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private int marksObtained;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionDate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(int marksObtained) {
		this.marksObtained = marksObtained;
	}

	public LocalDate getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(LocalDate admissionDate) {
		this.admissionDate = admissionDate;
	}
	@Transient
    @JsonProperty("grade")
    public String getGrade() {
        if (marksObtained > 40) {
            LocalDate now = LocalDate.now();
            Period period = Period.between(admissionDate, now);
            int months = period.getYears() * 12 + period.getMonths();

            if (marksObtained >= 90 && months <= 6) {
                return "Platinum";
            } else if (marksObtained >= 80 && marksObtained < 90 && months <= 6) {
                return "Merit";
            } else {
                return "Pass";
            }
        } else {
            return "Fail";
        }
    }

    
}

