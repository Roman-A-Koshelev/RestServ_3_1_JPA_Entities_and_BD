package ru.ibs.services.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ibs.services.domain.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByFirstName(String firstName);

    // Получить список непосредственных подчиненных руководителя
    List<Employee> findAllByBossId(Long bossId);

    // Получить список сотрудников, чья месячная заработная плата непосредственно больше, чем у их руководителей
    @Query("SELECT e1 FROM Employee e1 LEFT JOIN Employee e2 ON e1.boss.id = e2.id WHERE e2.id = :bossId AND e1.monthSalary > e2.monthSalary")
    List<Employee> findAllByMonthSalaryGreaterThanBossMonthSalary(Long bossId);
}
