package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.UserServiceImpl;

import java.util.List;

@Controller
public class LoginServlet {

    private Logger log;
    @Autowired
    FormValidator formValidator;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    public void setLog(Logger log) {
        this.log = log;
    }

    @RequestMapping(value = "loginPage", method = RequestMethod.GET)
    public String manageLoginPage(Model model) {
        return "login";
    }

    @RequestMapping(value = "mainPageUser", method = RequestMethod.POST)
    public ModelAndView doMainPageUser(@RequestParam("login") String login,
                                       @RequestParam("password") String password
    ) {
        ModelAndView mod = new ModelAndView();
        User user = userServiceImpl.getByLoginAndName(login, password);
        if (user != null) {
            mod.addObject("user", user);
            if ("admin".equals(user.getRole())) {
                log.info("public class LoginServlet--RequestMapping --mainPageUser ADMIN");
                List<User> listAllUsers = userServiceImpl.getAll();
                mod.addObject("listAllUsers", listAllUsers);
                mod.setViewName("/admin/usersEditData");
            } else {
                mod.setViewName("/user/mainUserPage");
            }
        } else {
            mod.setViewName("index");
        }
        return mod;
    }

    @RequestMapping(value = "signUp", method = RequestMethod.GET)
    public String manageRegistrationPage(Model model) {
        User user = new User();
        user.setUserName("imy");
        model.addAttribute("user", user);
        return "registration";
    }
//TODO change registration
    @RequestMapping(value = "registration", method = RequestMethod.POST)
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
            user.setPassword(password);
            user.setRole("user");
            user.setUserName("enter your name");
            user = userServiceImpl.addUser(user);
            mod.addObject("user", user);
            mod.setViewName("/user/userEditDataPage");
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
