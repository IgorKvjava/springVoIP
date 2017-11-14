package ua.kvelinskyi.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
//@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"ua.kvelinskyi.service.impl",
        "ua.kvelinskyi.repository"})
@EntityScan(basePackages = {"ua.kvelinskyi.entity"})
@EnableTransactionManagement
public class RepositoryConfiguration {


}
