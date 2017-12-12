package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.InformationDoctor;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.InformationDoctorServiceImpl;
import ua.kvelinskyi.service.impl.UserServiceImpl;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {

    private Logger log;
    //TODO Autowired Logger
    @Autowired
    public void setLog(Logger log) {
        this.log = log;
    }

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    InformationDoctorServiceImpl informationDoctorServiceImpl;

    private Integer dayOfDate (java.sql.Date date){
        String datePars = date.toString();
        String[] stringList = datePars.split("-");
        return Integer.parseInt(stringList[2]);
    }
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
    @RequestMapping("/admin/form39Admin")
    public ModelAndView doForm39() {
        log.info("class AdminController - page form-39 ");
        Date curTime = new Date();
        java.sql.Date currentDate = new java.sql.Date(curTime.getTime());
        ModelAndView mod = new ModelAndView();
        mod.addObject("dateStart", currentDate);
        mod.addObject("dateEnd", currentDate);
        mod.setViewName("/admin/form39Admin");
        return mod;
    }

    @RequestMapping("/admin/informationDoctor")
    public ModelAndView doSpecialtyOfDoctor() {
        log.info("class AdminController - edit Specialty Of Doctor");
        ModelAndView mod = new ModelAndView();
        List<InformationDoctor> informationDoctorList = informationDoctorServiceImpl.getAll();
        InformationDoctor informationDoctor = new InformationDoctor();
        mod.addObject("informationDoctor", informationDoctor);
        mod.addObject("informationDoctorList", informationDoctorList);
        mod.setViewName("/admin/informationDoctor");
        return mod;
    }
}
