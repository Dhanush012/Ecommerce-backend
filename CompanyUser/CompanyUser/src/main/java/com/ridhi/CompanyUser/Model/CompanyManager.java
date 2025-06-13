package com.ridhi.CompanyUser.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company_managers")
public class CompanyManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

//    ^                          // Start of string
//    (?=.*[a-z])                // At least one lowercase letter
//    (?=.*[A-Z])                // At least one uppercase letter
//    (?=.*\\d)                  // At least one digit
//    (?=.*[@#$^&*])             // At least one special character (@#$^&*)
//    (?!.*[_])                  // Does NOT contain underscore
//    (?!.*[@#$^&*]$)            // Does NOT end with special character
//    .{8,}                     // Minimum 8 characters
//    $                          // End of string


    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$^&*])(?!.*[_])(?!.*[@#$^&*]$).{8,}$",
            message = "Password must contain 1 uppercase, 1 lowercase, 1 number, 1 special char (@#$^&*), no underscores, and must not end with special character"
    )
    private String password;

    @Column(length = 10)
    private String mobileNumber;

    private String country;
    private String state;

    private String companyName;
    private String sector;
    private String registrationNumber;

    @Column(unique = true)
    private String empId;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}

