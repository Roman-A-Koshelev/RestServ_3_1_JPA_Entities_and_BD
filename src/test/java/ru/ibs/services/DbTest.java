package ru.ibs.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ibs.services.domain.Course;
import ru.ibs.services.domain.Department;
import ru.ibs.services.domain.Employee;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class DbTest {
    @PersistenceContext
    EntityManager em;

    @Before
    public void setup() {
        Department dep = new Department(null, "IT");
        Course course = new Course(null, "REST Service");
        Employee emp = new Employee(null, "a", "b", LocalDate.now(), dep, 500000, List.of(course));

        em.persist(dep);
        em.persist(course);
        em.persist(emp);
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
}
