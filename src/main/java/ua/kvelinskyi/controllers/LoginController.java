package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.UserServiceImpl;
import java.security.Principal;
import java.util.List;

@RestController
public class LoginController {

    private Logger log;
    //TODO Autowired log
    @Autowired
    public void setLog(Logger log) {
        this.log = log;
    }
    @Autowired
    FormValidator formValidator;
    @Autowired
    UserServiceImpl userServiceImpl;
//TODO link 111
    @RequestMapping(value = "/111")
    public ModelAndView get111() {
        ModelAndView modelAndView = new ModelAndView();
        log.info("class LoginServlet -IndexController(/) has started !");
        modelAndView.setViewName("11111");
        return modelAndView;
    }

    @RequestMapping(value = "/")
    public ModelAndView getIndexSlash() {
        ModelAndView modelAndView = new ModelAndView();
        log.info("class LoginServlet -IndexController(/) has started !");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/index")
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        log.info("class LoginServlet - IndexController(/index) has started !");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/loginPage")
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

    // Login form with error
    @RequestMapping("/login-error")
    public ModelAndView getLoginError() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("loginError", true);
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

    @RequestMapping(value = "/infoPage", method = RequestMethod.GET)
    public ModelAndView getInfoPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("info");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/usersEditData")
    public ModelAndView getMainUserPage(Authentication loggedUser) {
        ModelAndView mod = new ModelAndView();
                log.info("class LoginController - RequestMapping - mainPageUser ADMIN");
                List<User> listAllUsers = userServiceImpl.getAll();
                mod.addObject("listAllUsers", listAllUsers);
                mod.setViewName("/admin/usersEditData");
        return mod;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView getRegistrationPage() {
       // User user = new User();
        ModelAndView modelAndView = new ModelAndView();
        log.info("class LoginServlet - signUp has started !");
        modelAndView.setViewName("registration");
       // modelAndView.addObject("user", user);
        return modelAndView;
    }

    //TODO change registration
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView doRegistrationUser(@RequestParam("login") String login,
                                           @RequestParam("password") String password)
    {
        log.info("public class LoginController -- registration");
        ModelAndView mod = new ModelAndView();
        if (userServiceImpl.getByLogin(login)!=null) {
            log.info("class LoginController - registration Login isExist");
            mod.setViewName("registration");
        } else {
            User user = new User();
            user.setLogin(login);
            String cryptedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(cryptedPassword);
            user.setEnabled("true");
            user.setRole(2);
            user.setUserName("enter your name");
            user = userServiceImpl.addUser(user);
            log.info("class LoginController - registration new user"+user.getUserName());
            if(user!=null){
                mod.setViewName("index");
            }else {
                mod.setViewName("registration");
            }
        }
        return mod;
    }
    // TODO do'n use initValidator
    @InitBinder
    protected void initValidator(WebDataBinder binder) {
        // bind validator to controller
        binder.setValidator(this.formValidator);
    }

    @RequestMapping(value = "/user/mainUserPage", method = RequestMethod.POST)
    public ModelAndView doEditUser(){
        ModelAndView mod = new ModelAndView();
        return mod;
    }

    @RequestMapping(value = "/userUpdateData")
    public ModelAndView doUserEditData(@RequestParam("password") String password,
                                       @RequestParam("userName") String userName,
                                       @RequestParam("enabled") String enabled,
                                       @Validated
                                               User user) {
        ModelAndView mod = new ModelAndView();

        if (!password.equals("0")){
            String cryptedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(cryptedPassword);
        }
        user.setUserName(userName);
        user.setEnabled(enabled);
        user = userServiceImpl.editUser(user);
        mod.addObject("user", user);
        mod.setViewName("/user/userEditDataPage");
        return mod;
    }

    // for 403 access denied page
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public ModelAndView accessDenied(Principal user) {
        ModelAndView model = new ModelAndView();
        if (user != null) {
            model.addObject("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        } else {
            model.addObject("msg",
                    "You do not have permission to access this page!");
        }

        model.setViewName("accessDenied");
        return model;

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
