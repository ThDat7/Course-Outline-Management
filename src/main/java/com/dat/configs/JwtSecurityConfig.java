package com.dat.configs;

import com.dat.filters.JwtAuthenticationTokenFilter;
import com.dat.filters.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.dat.components",
        "com.dat.controllers",
        "com.dat.repository",
        "com.dat.service",
        "com.dat",
})
@Order(1)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable crsf cho đường dẫn /rest/**
        http.csrf().ignoringAntMatchers("/api/**");
        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/api/education-programs/remove-outline/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/course-outlines/").hasAuthority("ADMIN")
                .antMatchers("/api/assign-outlines/create/**").hasAuthority("ADMIN")

                // Teacher endpoints
                .antMatchers("/api/profile/teacher").hasAuthority("TEACHER")
                .antMatchers("/api/profile/additional-info/teacher").hasAuthority("TEACHER")
                .antMatchers(HttpMethod.GET, "/api/course-outlines/").hasAuthority("TEACHER")
                .antMatchers("/api/course-outlines/*").hasAuthority("TEACHER")

                // Student endpoints
                .antMatchers("/api/profile/student").hasAuthority("STUDENT")
                .antMatchers("/api/profile/additional-info/student").hasAuthority("STUDENT")

                // authenticated endpoints
                .antMatchers("/api/search/**").authenticated()
                .antMatchers("/api/comments/**").authenticated()
                .antMatchers("/api/course-outlines/view/**").authenticated()
                .antMatchers("/api/education-programs/view/**").authenticated()

                .antMatchers("/api/login", "/api/registry").permitAll()
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
