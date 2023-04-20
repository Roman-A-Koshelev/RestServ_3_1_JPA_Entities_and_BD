package ru.ibs.services.business;

import org.slf4j.Logger;
import ru.ibs.services.domain.entity.Employee;

public class GreetingsServiceImpl implements GreetingsService {
    private Logger log;

    public GreetingsServiceImpl(Logger log) {
        this.log = log;
    }

    @Override
    public void sayHello(Employee emp) {
        log.info("Hello " + emp.getFirstName());
    }
}
