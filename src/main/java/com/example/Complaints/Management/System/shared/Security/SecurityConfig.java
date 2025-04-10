package com.example.Complaints.Management.System.shared.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    @Lazy
    private  JWTFilter jwtFilter;
    @Autowired
    @Lazy
    private  UserDetailsService userDetailsService;

    // BAAAAAAAAAAAAAAAAAsic Authhhhhhhhhhhhhhhhhhhh
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/status/**").hasRole("ADMIN")// Only admins can access
//                        .requestMatchers("/api/user/**").hasRole("USER")   // Users can access
//                        .requestMatchers("/api/auth/**").permitAll()       // Public authentication endpoints
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(httpBasic ->{}); // Basic Authentication (Replace with JWT in production)
//        return http.build();
//    }



    // FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFForm LLLLLLLLLLLLLLogin
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/status/**").hasRole("ADMIN")// Only admins can access
//                        .requestMatchers("/api/user/**").hasRole("USER")   // Users can access
//                        .requestMatchers("/api/auth/**").permitAll()       // Public authentication endpoints
//                        .anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Enables session-based auth
//                );
//        return http.build();
//    }


        // JJJJJJJJWWWWWWWWWWWWWWWWWWWWWWWWTTTTTTTTTTTTTTTTTTT
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/status/**").hasRole("ADMIN")// Only admins can access
                        .requestMatchers("/api/user/**").hasRole("USER")   // Users can access
                        .requestMatchers("/api/auth/**").permitAll()// Public authentication endpoints
                        .requestMatchers("/api/test/**").permitAll()
                        .anyRequest().authenticated())
                        .addFilterBefore(
                                jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//    @Bean
//    public UserDetailsService customUserDetailsService() {
//        return new CustomUserDetailService(); // Properly injected
//    }
}
