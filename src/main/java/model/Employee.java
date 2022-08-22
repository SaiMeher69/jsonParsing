package model;

import Annotations.NameToName;

import java.util.Arrays;

public class Employee {
    private int id;
    @NameToName(value = "first_name")
    private String firstName;
    @NameToName(value = "last_name")
    private String lastName;
    @NameToName(value = "tasks")
    private String jobTitles;
    private int age;
    private String url;

    public Employee() {
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jobTitles=" + jobTitles +
                ", age=" + age +
                ", url='" + url + '\'' +
                '}';
    }
}
