package ru.ibs.services.rest.v2;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.ibs.services.business.SalaryService;
import ru.ibs.services.dto.EmployeeDtoMS;

import java.util.List;

@RestController
@RequestMapping("/v2/salary")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @GetMapping("/max/departments/{departmentId}")
    @Operation(operationId = "maxSalaryEmployeeInDep", summary = "Get max salary employee.")
    public EmployeeDtoMS maxSalaryEmployeeInDepartment(@PathVariable Long departmentId) {
        return salaryService.maxSalaryEmployeeInDepartment(departmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Department or employees not found."));
    }

    // Получить список сотрудников, чья месячная заработная плата непосредственно больше, чем у их руководителей
    @GetMapping("/greater/{bossId}")
    @Operation(operationId = "getEmployeesWithMonthSalaryGreaterThanBossMonthSalary", summary = "Get month salary employee greater then boss month salary.")
    public List<EmployeeDtoMS> getEmployeesWithMonthSalaryGreaterThanBossMonthSalary(@PathVariable Long bossId) {
        List<EmployeeDtoMS> empLs = salaryService.getEmployeesWithMonthSalaryGreaterThanBossMonthSalary(bossId);
        if (null == empLs) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Employees or Boss not found.");
        }

        return empLs;
    }
}
