package com.akshay.project.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akshay.project.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByActiveTrue();
}
