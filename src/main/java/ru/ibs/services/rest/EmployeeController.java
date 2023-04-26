package ru.ibs.services.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ibs.services.domain.EmployeeRepository;
import ru.ibs.services.domain.entity.Employee;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeRepository repo;

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repo.deleteById(id);
    }

    @GetMapping
    Iterable<Employee> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    Employee getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Employee newEmployee(@RequestBody Employee employee) {
        log.info("--------------------------------------------------------------------------------");
        if (employee.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Found Id. Use PUT instead of POST.");
        }
        return repo.save(employee);
    }
}
