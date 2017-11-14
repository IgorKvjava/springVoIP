package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kvelinskyi.service.impl.UserServiceImpl;

@Controller
public class AdminController {

    @Autowired
    private Logger log;

    @Autowired
    FormValidator formValidator;

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping("user/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "/user/userEditDataPage";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        userServiceImpl.delete(id);
        model.addAttribute("listAllUsers",userServiceImpl.getAll());
        return "/admin/usersEditData";
    }
}
