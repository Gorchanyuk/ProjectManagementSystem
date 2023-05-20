package digital.design.management.system.repository;


import digital.design.management.system.enumerate.StatusEmployee;
import digital.design.management.system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByUid(UUID uid);

    List<Employee> findAllByStatus(StatusEmployee statusEmployee);

    Optional<Employee> findByUsernameAndStatus(String username, StatusEmployee statusEmployee);

    @Query("SELECT e FROM Employee e WHERE (e.lastName ILIKE %:keyword% " +
            "OR e.firstName ILIKE %:keyword% " +
            "OR e.surname ILIKE %:keyword% " +
            "OR e.username ILIKE %:keyword% " +
            "OR e.email ILIKE %:keyword%) " +
            "AND e.status =:status")
    List<Employee> findByKeyword(String keyword, StatusEmployee status);


}
