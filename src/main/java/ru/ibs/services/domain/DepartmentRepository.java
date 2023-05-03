package ru.ibs.services.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.services.domain.entity.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
