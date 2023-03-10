package com.bonnieapps.springboot2app.config;

import com.bonnieapps.springboot2app.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    public final UserRepository userRepository;

      //@Bean annotation which is applied on a method to specify that it returns a bean to be managed by Spring context.
        /*
        * Bean life cycle is managed by the spring container.
        * When we run the program then, first of all, the spring container gets started.
        * After that, the container creates the instance of a bean as per the request, and then dependencies are injected.
        * And finally, the bean is destroyed when the spring container is closed.
        * Therefore, if we want to execute some code on the bean instantiation and just after closing the spring container,
        *  then we can write that code inside the custom init() method and the destroy() method.

        * */
    @Bean
    public UserDetailsService userDetailsService(){

        return username -> userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){ // responsible to search the user details
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
