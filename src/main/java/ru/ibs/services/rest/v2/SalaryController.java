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

@RestController
@RequestMapping("/v2/salary/max")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @GetMapping("/department/{departmentId}")
    @Operation(operationId = "maxSalaryEmployeeInDep", summary = "Get max salary employee.")
    public EmployeeDtoMS maxSalaryEmployeeInDepartment(@PathVariable Long departmentId) {
        return salaryService.maxSalaryEmployeeInDepartment(departmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Department or employees not found."));
    }
}
