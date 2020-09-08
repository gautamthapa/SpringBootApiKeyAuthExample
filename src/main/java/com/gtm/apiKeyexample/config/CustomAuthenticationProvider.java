/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtm.apiKeyexample.config;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author gtm
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${apikey.http.auth-token-header-name}")
    private String principalRequestHeader;

    @Value("${apikey.http.auth-token}")
    private String principalRequestValue;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        String principal = (String) authentication.getPrincipal();
        System.out.println("principal: " + principal);
        System.out.println("principalRequestValue: " + principal);
        System.out.println("Condition1: " + principalRequestValue.equalsIgnoreCase(principal));
        System.out.println("Condition2: " + principalRequestValue.equals(principal));
        if (!principalRequestValue.equals(principal)) {
            System.out.println("ERRRRRRRRRRRRRRRRORRRRRRRRR");
            throw new BadCredentialsException("The API key was not found or not the expected value.");
        }
        authentication.setAuthenticated(true);

        return authentication;
//        if (shouldAuthenticateAgainstThirdPartySystem()) {
// 
//            // use the credentials
//            // and authenticate against the third-party system
//            return new UsernamePasswordAuthenticationToken(
//              name, password, new ArrayList<>());
//        } else {
//            return null;
//        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
