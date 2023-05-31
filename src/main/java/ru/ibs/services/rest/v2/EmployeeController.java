package ru.ibs.services.rest.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ibs.services.business.EmployeeService;
import ru.ibs.services.domain.EmployeeRepository;
import ru.ibs.services.domain.entity.Employee;
import ru.ibs.services.dto.EmployeeDtoMS;

import java.util.List;

@RestController("employee controller v2")
@RequestMapping("/v2/employees")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

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

    // Получить список непосредственных подчиненных руководителя (Get a list of the manager's direct subordinates)
    @GetMapping("/subordinates/{bossId}")
    @Operation(operationId = "getAllEmployeesByBossId", summary = "Получить список непосредственных подчиненных руководителя")
    public List<EmployeeDtoMS> getAllEmployeesByBossId(@PathVariable Long bossId) {
        List<EmployeeDtoMS> empLs = employeeService.getAllEmployeesByBossId(bossId);
        if (null == empLs || empLs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employees or Boss not found.");
        }

        return empLs;
    }

    // Получить непосредственного руководителя сотрудника
    @GetMapping("/bosses/{empId}")
    @Operation(operationId = "getBossOf", summary = "Получить непосредственного руководителя сотрудника")
    public EmployeeDtoMS getBossOf(Long empId) {
        return employeeService.getBossOf(empId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Employee or Boss not found."));
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
