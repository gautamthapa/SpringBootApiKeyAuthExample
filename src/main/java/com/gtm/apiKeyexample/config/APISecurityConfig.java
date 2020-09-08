/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtm.apiKeyexample.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author gtm
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${apikey.http.auth-token-header-name}")
    private String principalRequestHeader;

    @Value("${apikey.http.auth-token}")
    private String principalRequestValue;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

//    @Autowired
//    private CustomAuthenticationProvider authProvider;
    @Autowired
    private CustomAuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
//        System.out.println("");
//        filter.setAuthenticationManager(new AuthenticationManager() {
//
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                String principal = (String) authentication.getPrincipal();
//                System.out.println("principal: " + principal);
//                System.out.println("principalRequestValue: " + principal);
//                System.out.println("Condition1: " + principalRequestValue.equalsIgnoreCase(principal));
//                System.out.println("Condition2: " + principalRequestValue.equals(principal));
//                if (!principalRequestValue.equals(principal)) {
//                    System.out.println("ERRRRRRRRRRRRRRRRORRRRRRRRR");
//                    throw new BadCredentialsException("The API key was not found or not the expected value.");
//                }
//                authentication.setAuthenticated(true);
//                return authentication;
//            }
//        });

        httpSecurity.antMatcher("/**")
                .addFilterAfter(preAuthenticationFilter(), APIKeyAuthFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf().disable();

//        httpSecurity.
//                antMatcher("/**").
//                csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unAuthorizedHandler).and().
//                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
//                and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public APIKeyAuthFilter preAuthenticationFilter() {
        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
        System.out.println("************* preAuthenticationFilter");
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

//    @Override
//    protected AuthenticationManager authenticationManager() {
//        return new ProviderManager(Collections.singletonList(authProvider));
//    }
}
