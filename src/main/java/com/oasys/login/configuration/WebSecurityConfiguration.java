package com.oasys.login.configuration;

import com.oasys.login.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MyUserDetailsService userDetailsService;
    
    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       /* http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasAuthority("admin").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home/admin")
                .usernameParameter("user_name")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling().accessDeniedPage("/403")
                .accessDeniedPage("/access-denied");
         */ 
    	http.csrf().disable();
    	 
         // The pages does not require login
         http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();
  
         // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
         // If no login, it will redirect to /login page.
         http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('user', 'admin')");
  
         // For ADMIN only.
         //http.authorizeRequests().antMatchers("/admin").access("hasRole('admin')");
         //http.authorizeRequests().antMatchers("/admin/**").hasAuthority("admin").anyRequest();
         http.authorizeRequests().antMatchers("/admin/**").hasAuthority("admin");
         // When the user has logged in as XX.
         // But access a page that requires role YY,
         // AccessDeniedException will be thrown.
         http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
  
         // Config for Login Form
         http.authorizeRequests().and().formLogin()//
                 // Submit URL of login page.
                // .loginProcessingUrl("/j_spring_security_check") // Submit URL
                 //.loginPage("/login")//
                 .loginPage("/login")
                 .defaultSuccessUrl("/viewusers")//
                 .failureUrl("/login?error=true")//
                 .usernameParameter("user_name")//
                 .passwordParameter("password")
                 // Config for Logout Page
                 .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
                 
    
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

} 