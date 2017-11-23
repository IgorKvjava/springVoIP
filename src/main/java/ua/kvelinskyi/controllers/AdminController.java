package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.UserServiceImpl;

@Controller
public class AdminController {

    private Logger log;
    //TODO Autowired Logger
    @Autowired
    public void setLog(Logger log) {
        this.log = log;
    }

    @Autowired
    FormValidator formValidator;

    @Autowired
    UserServiceImpl userServiceImpl;
        //TODO decode password
    @RequestMapping("/admin/user/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        log.info("class AdminController - edit user id= "+id );
        User user = userServiceImpl.getUserById(id);
       // String pass = user.getPassword();
       // byte[] decodedBytes = Base64.getDecoder().decode(pass);
      //  pass = new String(decodedBytes);
       // pass = new String(Base64.decodeBase64(pass.getBytes()));
        //user.setPassword(pass);
        //import java.util.Base64;
        model.addAttribute("user", user);
        return "/user/userEditDataPage";
    }

    @RequestMapping("/admin/user/delete/{id}")
    public String delete(@PathVariable Integer id, Model model){
        log.info("class AdminController - delete user id= "+id);
        userServiceImpl.delete(id);
        model.addAttribute("listAllUsers",userServiceImpl.getAll());
        return "/admin/usersEditData";
    }
}
