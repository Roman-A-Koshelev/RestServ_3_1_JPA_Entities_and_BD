package ru.ibs.services.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ibs.services.domain.EmployeeRepository;
import ru.ibs.services.domain.entity.Employee;
import ru.ibs.services.dto.EmployeeDtoMS;
import ru.ibs.services.dto.mapper.EmployeeMapper;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private EmployeeMapper mapper;

    // Получить список непосредственных подчиненных руководителя
    public List<EmployeeDtoMS> getAllEmployeesByBossId(Long bossId) {
        List<Employee> empLs = empRepo.findAllByBossId(bossId);
        if (null == empLs || empLs.isEmpty()) {
            return null;
        }

        return empLs.stream().map((emp) -> mapper.toDto(emp)).toList();
    }

    // Получить непосредственного руководителя сотрудника
    public Optional<EmployeeDtoMS> getBossOf(Long empId) {
        Optional<Employee> empOpt = empRepo.findById(empId);
        if (empOpt.isEmpty() || null == empOpt.get().getBoss()) {
            return Optional.empty();
        }

        return Optional.of(mapper.toDto(empOpt.get().getBoss()));
    }
}
