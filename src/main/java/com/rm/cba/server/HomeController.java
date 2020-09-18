package com.rm.cba.server;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class HomeController {
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/home")
    public String home(Model model, Principal principal) {
        UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("username", currentUser.getUsername());
        return "home";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public RedirectView login(Model model, Principal principal) {
        UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("pnnl://login?username="+currentUser.getUsername());
        return redirectView;
    }
}