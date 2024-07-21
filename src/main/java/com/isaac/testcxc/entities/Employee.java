package com.isaac.testcxc.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(schema = "test", name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "agency_id")
    private Long agencyId;

    @Column(name = "profession_id")
    private Long professionId;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "ethnicity_id")
    private Long ethnicityId;

    @Column(name = "monthly_salary")
    private Double monthlySalary;

    private String md5;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Employee employee)) {
            return false;
        }

        return Objects.equals(this.md5, employee.getMd5());
    }

    @Override
    public int hashCode() {
        return Objects.hash(md5);
    }
}