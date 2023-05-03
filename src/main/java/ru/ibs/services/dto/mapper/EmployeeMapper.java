package ru.ibs.services.dto.mapper;

import org.mapstruct.Mapper;
import ru.ibs.services.domain.entity.Employee;
import ru.ibs.services.dto.EmployeeDtoMS;

@Mapper
public interface EmployeeMapper {
    EmployeeDtoMS toDto(Employee employee);
}
