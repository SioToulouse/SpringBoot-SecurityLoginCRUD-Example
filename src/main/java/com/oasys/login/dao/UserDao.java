package com.oasys.login.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasys.login.model.User;
import com.oasys.login.repository.UserRepository;

@Service // si absent message erreu sur accès à un objet de type bean sur @Autowired
public class UserDao {
	// Déclaration d'un attribut permettant d'accéder aux méthodes définies dans l'interface UsersRepository
	@Autowired
	UserRepository userRepository;
	
	
	public List<User> findAll(){
		List<User> lstUsers = userRepository.findAll();
		return lstUsers;
		
	}
	/*
	public Optional<Users> findByEmail(String email){
		Optional<Users> user = usersRepository.findByEmail(email);
		return user;
		
	}
	*/
	public User findByEmail(String email){
		User user = userRepository.findByEmail(email);
		return user;
	}
	
	
}
