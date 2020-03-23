package com.oasys.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oasys.login.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findAll();
    User findByEmail(String email);
    User findByUserName(String userName);
    Optional<User> findById(int id);
}
