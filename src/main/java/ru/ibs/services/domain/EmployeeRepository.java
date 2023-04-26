package ru.ibs.services.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.services.domain.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByFirstName(String firstName);
}
