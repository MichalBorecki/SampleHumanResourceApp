package pl.borecki.sampleHumanResourceApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.borecki.sampleHumanResourceApp.model.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);

}
