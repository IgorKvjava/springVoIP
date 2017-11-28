package ua.kvelinskyi.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kvelinskyi.entity.Form39;

public interface Form39Repository extends JpaRepository<Form39, Integer> {

    List<Form39> findByDateNowAndIdDoctor(Date date, Integer idDco);
}
