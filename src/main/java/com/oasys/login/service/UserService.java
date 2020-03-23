package com.oasys.login.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oasys.login.model.Role;
import com.oasys.login.model.User;
import com.oasys.login.repository.RoleRepository;
import com.oasys.login.repository.UserRepository;

/*
 * Classe permettant la création d'un objet de type userService pour l'accès aux données d'un user
 *  et définition de méthodes utilisables sur un objet de type userService.
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
     * Dans le constructeur ci-dessous, quand on appelle le constructeur on instancie des objets de type
     *  userRepository,roleRepository,bCryptPasswordENcode
     */
    @Autowired  
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /*
     * Méthode appelée pour trouver un user par son userName
     */
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    
    public Optional<User> findById(int id) {
    	return userRepository.findById(id);
    }

    /*
     * méthod permettant d'enregistrer un user, méthode save(user), dans la table users de la BDD - voir annotations de la classe User
     * @Entity
	 *	@Table(name = "users")
	 *	public class User {..}
     */
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));//chiffrement du mot de passe à l'enregistrement dans la BDD
        user.setActive(true);
        Role userRole = roleRepository.findByRole("admin");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}