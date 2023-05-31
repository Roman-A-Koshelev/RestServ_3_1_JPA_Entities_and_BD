package ru.ibs.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ibs.services.domain.DepartmentRepository;
import ru.ibs.services.domain.EmployeeRepository;
import ru.ibs.services.domain.entity.Course;
import ru.ibs.services.domain.entity.Department;
import ru.ibs.services.domain.entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
@ActiveProfiles("test")
public class DbTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    DepartmentRepository depRepo;
    @Autowired
    EmployeeRepository empRepo;

    @Before
    public void setup() {
        Department dep = new Department(null, "IT", null);
        Course course = new Course(null, "REST Service");
        Employee emp1 = new Employee(null, "a", "s", LocalDate.now(), dep, 500000, null, List.of(course));
        dep.setEmployees(List.of(emp1));

        em.persist(dep);
        em.persist(course);
        em.persist(emp1);

        Employee boss = empRepo.findAllByFirstName("a").get(0);
        dep = depRepo.findAllByName("IT").get(0);
        course = em.createQuery("FROM Course", Course.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);
        Employee emp2 = new Employee(null, "b", "t", LocalDate.now(), dep, 300000, boss, List.of(course));
        Employee emp3 = new Employee(null, "c", "u", LocalDate.now(), dep, 1000000, boss, List.of(course));
        Employee emp4 = new Employee(null, "d", "v", LocalDate.now(), dep, 500000, boss, List.of(course));
        em.persist(emp2);
        em.persist(emp3);
        em.persist(emp4);
    }

    @Test
    public void test() {
        Assert.assertEquals(1, em.createQuery("FROM Department").getResultList().size()); // Запрос пишем на HQL, то есть Сущности вместо Таблиц
        Employee emp = em.createQuery("FROM Employee", Employee.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);
        Assert.assertEquals("REST Service", emp.getCourses().get(0).getName());
    }

    @Test
    public void test2() {
        Employee emp = empRepo.findAllByFirstName("a").get(0);
        Assert.assertEquals("REST Service", emp.getCourses().get(0).getName());
    }

    // Тест для "получить список непосредственных подчиненных руководителя"
    @Test
    public void testFindAllByBossId() {
        Employee boss = empRepo.findAllByFirstName("a").get(0);
        Assert.assertEquals(4, empRepo.count());

        List<Employee> empLs = empRepo.findAllByBossId(boss.getId());
        Assert.assertEquals(3, empLs.stream().count());
    }

    // Тест для "получить непосредственного руководителя сотрудника"
    @Test
    public void testGetBossResultIsNull() {
        Employee emp = empRepo.findAllByFirstName("a").get(0);
        Assert.assertNull(emp.getBoss());
    }
    // Тест для "получить непосредственного руководителя сотрудника"
    @Test
    public void testGetBossResultIsNotNull() {
        Employee emp = empRepo.findAllByFirstName("c").get(0);
        Assert.assertNotNull(emp.getBoss());

        Employee boss = empRepo.findAllByFirstName("a").get(0);
        Assert.assertEquals(boss, emp.getBoss());
    }
}
