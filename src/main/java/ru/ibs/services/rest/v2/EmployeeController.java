package ru.ibs.services.rest.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ibs.services.domain.EmployeeRepository;
import ru.ibs.services.domain.entity.Employee;

@RestController("employee controller v2")
@RequestMapping("/v2/employees")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeRepository repo;

    @GetMapping
    Iterable<Employee> getAll(@RequestParam(required = false) String firstName) {
        if (null != firstName) {
            return repo.findAllByFirstName(firstName);
        }

        return repo.findAll();
    }

    @GetMapping("/{id}")
    Employee getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "addEmp", summary = "Add new employee.")
    Employee newEmployee(@RequestBody Employee employee) {
        log.info("--------------------------------------------------------------------------------");
        if (employee.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Found Id. Use PUT instead of POST.");
        }
        return repo.save(employee);
    }
}
