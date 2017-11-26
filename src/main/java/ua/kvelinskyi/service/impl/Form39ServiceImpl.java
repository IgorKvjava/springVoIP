package ua.kvelinskyi.service.impl;

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
}
