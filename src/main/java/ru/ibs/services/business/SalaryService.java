package ru.ibs.services.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.services.domain.DepartmentRepository;
import ru.ibs.services.domain.EmployeeRepository;
import ru.ibs.services.domain.entity.Department;
import ru.ibs.services.domain.entity.Employee;
import ru.ibs.services.dto.EmployeeDtoMS;
import ru.ibs.services.dto.mapper.EmployeeMapper;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryService {
    @Autowired
    private DepartmentRepository depRepo;

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private EmployeeMapper mapper;

    public Optional<EmployeeDtoMS> maxSalaryEmployeeInDepartment(Long depId) {
        Optional<Department> depOpt = depRepo.findById(depId);
        if (depOpt.isEmpty() || depOpt.get().getEmployees().isEmpty()) {
            return Optional.empty();
        }

        Employee employee = depOpt.get().getEmployees().stream()
                .max((e1,e2) -> e1.getMonthSalary().compareTo(e2.getMonthSalary())).get();
        return Optional.of(mapper.toDto(employee));
    }

    // Получить список сотрудников, чья месячная заработная плата непосредственно больше, чем у их руководителей
    public List<EmployeeDtoMS> getEmployeesWithMonthSalaryGreaterThanBossMonthSalary(Long bossId) {
        List<Employee> empLs = empRepo.findAllByMonthSalaryGreaterThanBossMonthSalary(bossId);
        if (null == empLs || empLs.isEmpty()) {
            return null;
        }

        return empLs.stream().map((emp) -> mapper.toDto(emp)).toList();
    }
}
