package eu.neveux.springbootapi.repository;

import org.springframework.stereotype.Repository;

import eu.neveux.springbootapi.model.Employee;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    
}
