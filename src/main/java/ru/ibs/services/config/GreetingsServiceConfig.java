package ru.ibs.services.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.ibs.services.business.GreetingsService;
import ru.ibs.services.business.GreetingsServiceImpl;
import ru.ibs.services.business.GreetingsServiceImpl2;
import ru.ibs.services.domain.entity.Employee;

@Configuration
@Slf4j
public class GreetingsServiceConfig {
    @Bean
    @Profile("!test")
    GreetingsService getGSImpl() {
        return new GreetingsServiceImpl(log);
    }

    @Bean
    @Profile("test")
    GreetingsService getGSImpl2() {
        return new GreetingsServiceImpl2(log);
    }

    @Bean
    Employee makeDefEmpl() {
        return new Employee(null, "Nikita", null, null, null, null, null, null);
    }
}
