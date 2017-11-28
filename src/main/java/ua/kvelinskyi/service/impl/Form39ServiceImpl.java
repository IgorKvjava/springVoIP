package ua.kvelinskyi.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kvelinskyi.entity.Form39;
import ua.kvelinskyi.repository.Form39Repository;
import ua.kvelinskyi.service.Form39Service;

@Service
public class Form39ServiceImpl implements Form39Service {
    @Autowired
    Form39Repository form39Repository;

    @Override
    public Form39 addForm39(Form39 form39) {
        return form39Repository.save(form39);
    }

    @Override
    public List<Form39> dateNowIsPresent(Date date, int idDoc) {
        return form39Repository.findByDateNowAndIdDoctor(date, idDoc);
    }
}
