package ru.ibs.services.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDtoMS {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Integer monthSalary;
}
