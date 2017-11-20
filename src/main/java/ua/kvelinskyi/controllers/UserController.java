package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.Form39;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private Logger log;
    //TODO Autowired Logger
    @Autowired
    public void setLog(Logger log) {
        this.log = log;
    }

    @Autowired
    UserServiceImpl userServiceImpl;


    @RequestMapping("/user/form39")
    public ModelAndView doEditForm39(Authentication loggedUser) {
        String loginUser = loggedUser.getName();
        User authenticationUser = userServiceImpl.getByLogin(loginUser);
        ModelAndView mod = new ModelAndView();
        mod.setViewName("/user/form39");

        return mod;
    }
}
