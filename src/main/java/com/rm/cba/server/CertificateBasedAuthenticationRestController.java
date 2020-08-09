package com.rm.cba.server;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api")
public class CertificateBasedAuthenticationRestController {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ResponseEntity<?> home(Model model, Principal principal) {
        UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("username", currentUser.getUsername());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
