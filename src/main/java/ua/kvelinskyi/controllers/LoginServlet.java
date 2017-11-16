package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
public class LoginServlet {

    private Logger log;
    //TODO Autowired
    @Autowired
    public void setLog(Logger log) {
        this.log = log;
    }
    @Autowired
    FormValidator formValidator;
    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/")
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        log.info("IndexController has started !");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/loginPage")
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

    @RequestMapping(value = "/infoPage", method = RequestMethod.GET)
    public ModelAndView getInfoPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info");
        return modelAndView;
    }


    @RequestMapping(value = "/mainUserPage")
    public ModelAndView getMainUserPage(Principal loggedUser) {
        ModelAndView mod = new ModelAndView();
                log.info("public class LoginServlet--RequestMapping --mainPageUser ADMIN");
                List<User> listAllUsers = userServiceImpl.getAll();
                mod.addObject("listAllUsers", listAllUsers);
                mod.setViewName("/admin/usersEditData");
        return mod;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView getRegistrationPage() {
        User user = new User();
        ModelAndView modelAndView = new ModelAndView();
        log.info("signUp has started !");
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
//TODO change registration
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView doRegistrationUser(@RequestParam("login") String login,
                                           @RequestParam("password") String password)
    {
        log.info("public class LoginServlet--RequestMapping --registration");
        ModelAndView mod = new ModelAndView();
        if (userServiceImpl.getByLogin(login)!=null) {
            mod.setViewName("registration");
        } else {
            User user = new User();
            user.setLogin(login);
            String cryptedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(cryptedPassword);
            user.setRole("USER");
            user.setUserName("enter your name");
            user = userServiceImpl.addUser(user);
            mod.addObject("user", user);
            mod.setViewName("index");
        }
        return mod;
    }

    @InitBinder
    protected void initValidator(WebDataBinder binder) {
        // bind validator to controller
        binder.setValidator(this.formValidator);
    }


    @RequestMapping(value = "userUpdateData", method = RequestMethod.POST)
    public ModelAndView doUserEditData(@RequestParam("password") String password,
                                       @RequestParam("userName") String userName,
                                       @Validated
                                               User user) {
        ModelAndView mod = new ModelAndView();
        user.setPassword(password);
        user.setUserName(userName);
        user = userServiceImpl.editUser(user);
        mod.addObject("user", user);
        mod.setViewName("/user/userEditDataPage");
        return mod;
    }

    /*@RequestMapping(value = "editUserDataPage", method = RequestMethod.POST)
    public ModelAndView doUserEditDataPage(
            @Validated
                    User user) {
        user = @ModelAttribute("user");
        ModelAndView mod = new ModelAndView();
        mod.addObject("user", user);
        mod.setViewName("/user/userEditDataPage");
        return mod;
    }*/
}
