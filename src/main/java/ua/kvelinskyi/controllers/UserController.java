package ua.kvelinskyi.controllers;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.kvelinskyi.entity.Form39;
import ua.kvelinskyi.entity.User;
import ua.kvelinskyi.service.impl.Form39ServiceImpl;
import ua.kvelinskyi.service.impl.UserServiceImpl;

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

    private Integer dayOfDate (java.sql.Date date){
        String datePars = date.toString();
        String[] stringList = datePars.split("-");
        return Integer.parseInt(stringList[2]);
    }

    @RequestMapping("/user/form39")
    public ModelAndView doForm39(Authentication loggedUser) {
        log.info("class UserController - page form-39 for new data ");
        String loginUser = loggedUser.getName();
        User authenticationUser = userServiceImpl.getByLogin(loginUser);
        Form39 form39 = new Form39();
        Date curTime = new Date();
        java.sql.Date currentDate = new java.sql.Date(curTime.getTime());
        form39.setDateNow(currentDate);
        form39.setNumDay(dayOfDate(currentDate));
        ModelAndView mod = new ModelAndView();
        mod.addObject("UserId", authenticationUser.getId());
        // form39.setAdultsVisitsDisease(5);
        mod.addObject("form39", form39);
        mod.setViewName("/user/form39");
        return mod;
    }

    //TODO don't repeat this page ---if(listForm39ByDateNow.isEmpty())
    @RequestMapping("/user/form39/{id}")
    public ModelAndView doEditForm39(@PathVariable Integer id,
                                     Form39 form39) {
       //TODO LocalDate tt= chang
        log.info("class UserController - page form-39 select data ");
        //Date curTime = new Date();
        java.sql.Date dateForNewField = form39.getDateNow();
        //String DATE_FORMAT = "yyyy-MM-dd";
        //java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        //java.sql.Date currentDate = java.sql.Date.valueOf(sdf.format(curTime));
        List<Form39> listForm39ByDateNow = form39ServiceImpl.dateNowIsPresent(dateForNewField, id);
            log.info("----------listForm39ByDateNow--" + listForm39ByDateNow);
            int day = dayOfDate(dateForNewField);
        if (listForm39ByDateNow.isEmpty()) {
            //TODO set id 0 else id=(current id user), form39 update
            form39.setId(0);
            form39.setNumDay(day);
            form39.setIdDoctor(id);
            form39.setUser(userServiceImpl.getUserById(id));
            log.info("class UserController - page form-39 select data " + form39);
            form39ServiceImpl.addForm39(form39);
        }
        List<Form39> form39List = form39ServiceImpl.dateNowIsPresent(dateForNewField, id);
        ModelAndView mod = new ModelAndView();
        mod.addObject("form39List", form39List);
        mod.setViewName("/user/form39Data");
        return mod;
    }

    @RequestMapping("/user/form39Data")
    public ModelAndView doViewForm39Data(Authentication loggedUser) {
        ModelAndView mod = new ModelAndView();
        List<Integer> id = userServiceImpl.getUserIdByLogin(loggedUser.getName());
        Date curTime = new Date();
        java.sql.Date currentDate = new java.sql.Date(curTime.getTime());
        List<Form39> listForm39ByDateNow = form39ServiceImpl.dateNowIsPresent(currentDate, id.get(0));
        mod.addObject("form39List", listForm39ByDateNow);
        mod.setViewName("/user/form39Data");
        return mod;
    }
}
