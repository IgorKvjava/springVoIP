package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.Form39;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.Form39ServiceImpl;
import ua.kvelinskyi.service.impl.UserServiceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    Form39ServiceImpl form39ServiceImpl;


    @RequestMapping("/user/form39")
    public ModelAndView doForm39(Authentication loggedUser)
    {
        log.info("class UserController - page form-39 for new data ");
        String loginUser = loggedUser.getName();
        User authenticationUser = userServiceImpl.getByLogin(loginUser);
        Form39 form39 = new Form39();
        ModelAndView mod = new ModelAndView();
        mod.addObject("UserId", authenticationUser.getId());
       // form39.setAdultsVisitsDisease(5);
        mod.addObject("form39", form39);
        mod.setViewName("/user/form39");
        return mod;
    }

    @RequestMapping("/user/form39/{id}")
    public ModelAndView doEditForm39(@PathVariable Integer id,
                                     Form39 form39)
    {
        log.info("class UserController - page form-39 select data ");
        //TODO set id 0 else id=id form39
        form39.setId(0);
        form39.setIdDoctor(id);
        form39.setUser(userServiceImpl.getUserById(id));
        Date curTime = new Date();
        java.sql.Date myAnotherDate = new java.sql.Date(curTime.getTime());
        form39.setDateNow(myAnotherDate);
        log.info("class UserController - page form-39 select data "+form39);
        form39ServiceImpl.addForm39(form39);
        List<Form39> form39List = userServiceImpl.getListForm39User(id);
        ModelAndView mod = new ModelAndView();
        mod.addObject("form39List", form39List);
        mod.setViewName("/user/form39Data");
        return mod;
    }
}
