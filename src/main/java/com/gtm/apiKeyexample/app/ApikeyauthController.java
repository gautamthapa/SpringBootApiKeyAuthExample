/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtm.apiKeyexample.app;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gtm
 */
@RestController
public class ApikeyauthController {

    @RequestMapping(value = "/echo/{message}", method = RequestMethod.GET)
    public ResponseEntity testEcho(@PathVariable String message) {
        return new ResponseEntity(message, HttpStatus.OK);
    }

}