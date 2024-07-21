package com.isaac.testcxc.repositories;

import com.isaac.testcxc.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {

}
