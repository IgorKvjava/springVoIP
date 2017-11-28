package ua.kvelinskyi.service;

import java.sql.Date;
import java.util.List;

import ua.kvelinskyi.entity.Form39;

public interface Form39Service {

    Form39 addForm39(Form39 form39);
    List<Form39> dateNowIsPresent(Date date, int idDoc);
}
