package com.dat.configs;

import com.dat.filters.JwtAuthenticationTokenFilter;
import com.dat.filters.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

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
    @Autowired
    private UserStatusAccessDecisionVoter userStatusAccessDecisionVoter;

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
    public AccessDecisionManager accessDecisionManager() {
        return new UnanimousBased(List.of(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter()
                ,userStatusAccessDecisionVoter
        ));
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable crsf cho đường dẫn /rest/**
        http.cors(Customizer.withDefaults());
        http.csrf().ignoringAntMatchers("/api/**");
        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
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
                .antMatchers("/api/users/**").authenticated()

                .antMatchers("/api/login", "/api/registry").permitAll()

                .antMatchers("/api/**").authenticated()
                .accessDecisionManager(accessDecisionManager())

                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
