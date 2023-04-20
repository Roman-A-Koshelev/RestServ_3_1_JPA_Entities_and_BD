package ru.ibs.services.business;

import org.slf4j.Logger;
import ru.ibs.services.domain.entity.Employee;

public class GreetingsServiceImpl2 implements GreetingsService {
    private Logger log;

    public GreetingsServiceImpl2(Logger log) {
        this.log = log;
    }

    @Override
    public void sayHello(Employee emp) {
        log.info("Hello from test version " + emp.getFirstName());
    }
}
