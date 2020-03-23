package com.oasys.login.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;

import com.oasys.login.model.Role;
import com.oasys.login.model.User;

/*
 * Création d'une classe MyUserDetailsService personnalisée qui implémente UserDetailsService
 * permettant de redéfinir la classe initiale loadUserByUsername. Chargement des infos d'un user
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    
    /*
     * Personnalisation de la méthode d'origine loadUserByUsername pour renvoyer le user et ses autorisations en fonction
     * de ses rôles. On utilise 2 autres méthodes définies en dessous:
     * getUserAuthority et buildUserForAuthentication
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(userName); // findUserByUserName, méthode définie dans UserService
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());// on récupère les rôles en plus
        return buildUserForAuthentication(user, authorities);
    }

    /*
     * renvoie la liste des rôles pour un user donné
     */
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    /*
     * renvoie détails de connexion d'un user
     */
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.getActive(), true, true, true, authorities);
    }
}