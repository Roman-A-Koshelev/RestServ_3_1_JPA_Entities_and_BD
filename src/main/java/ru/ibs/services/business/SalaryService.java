package ru.ibs.services.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.services.domain.DepartmentRepository;
import ru.ibs.services.domain.entity.Department;
import ru.ibs.services.domain.entity.Employee;

import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    private DepartmentRepository depRepo;

    public Optional<Employee> maxSalaryEmployeeInDepartment(Long depId) {
        Optional<Department> depOpt = depRepo.findById(depId);
        if (depOpt.isEmpty() || depOpt.get().getEmployees().isEmpty()) {
            return Optional.empty();
        }

        Employee employee = depOpt.get().getEmployees().stream()
                .max((e1,e2) -> e1.getMonthSalary().compareTo(e2.getMonthSalary())).get();
        return Optional.of(employee);
    }
}
