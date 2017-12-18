package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.Form39;
import ua.kvelinskyi.entity.InformationDoctor;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.Form39ServiceImpl;
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
    Form39ServiceImpl form39ServiceImpl;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    InformationDoctorServiceImpl informationDoctorServiceImpl;

    @Autowired
    ControllerHelper controllerHelper;

    private Integer dayOfDate (java.sql.Date date){
        String datePars = date.toString();
        String[] stringList = datePars.split("-");
        return Integer.parseInt(stringList[2]);
    }

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

    @RequestMapping(value = "/admin/usersEditData")
    public ModelAndView doEditUserData() {
        ModelAndView mod = new ModelAndView();
        log.info("class AdminController - RequestMapping usersEditData");
        List<User> listAllUsers = userServiceImpl.getAll();
        mod.addObject("listAllUsers", listAllUsers);
        mod.setViewName("/admin/usersEditData");
        return mod;
    }

    @RequestMapping("/admin/user/onOff/{id}")
    public String doOnOffUser(@PathVariable Integer id, Model model){
        User user = userServiceImpl.getUserById(id);
        if(user.getEnabled().equals("true")){
            user.setEnabled("false");
            log.info("class AdminController - disable user id= "+id);
        }else {
            user.setEnabled("true");
            log.info("class AdminController - enable user id= "+id);
        }
        userServiceImpl.editUser(user);
        model.addAttribute("listAllUsers",userServiceImpl.getAll());
        return "/admin/usersEditData";
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

    @RequestMapping("/admin/informationDoctor/add")
    public ModelAndView doAddSpecialtyOfDoctor(InformationDoctor newInformationDoctor) {
        log.info("class AdminController - add Specialty Of Doctor");
        ModelAndView mod = new ModelAndView();
        informationDoctorServiceImpl.addInformationDoctor(newInformationDoctor);
        List<InformationDoctor> informationDoctorList = informationDoctorServiceImpl.getAll();
        InformationDoctor informationDoctor = new InformationDoctor();
        mod.addObject("informationDoctor", informationDoctor);
        mod.addObject("informationDoctorList", informationDoctorList);
        mod.setViewName("/admin/informationDoctor");
        return mod;
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
    @RequestMapping("/admin/form39Data/timeInterval")
    public ModelAndView doViewForm39DataOfAllDoctor(@RequestParam("dateStart") java.sql.Date dateStart,
                                                    @RequestParam("dateEnd") java.sql.Date dateEnd)
    {
        log.info("class AdminController - View Form39 Data Of All Doctor");
        ModelAndView mod = new ModelAndView();
        List<Form39> listForm39 = form39ServiceImpl.dataForm39ByTimeInterval(dateStart, dateEnd);
        mod.addObject("sumForms39", controllerHelper.sumForms39Entity(listForm39));
        mod.addObject("form39List", listForm39);
        mod.addObject("dateStart", dateStart);
        mod.addObject("dateEnd", dateEnd);
        mod.setViewName("/admin/form39Admin");
        return mod;
    }


}
