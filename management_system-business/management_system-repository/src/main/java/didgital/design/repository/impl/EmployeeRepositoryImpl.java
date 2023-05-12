package didgital.design.repository.impl;

import didgital.design.entity.Employee;
import didgital.design.repository.BaseRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class EmployeeRepositoryImpl implements BaseRepository<Employee> {

    private final Path file;
    protected final AtomicLong idGenerator;

    public EmployeeRepositoryImpl() {
        file = Paths.get("employee.model");
        List<Employee> employees = getAll();
        if (employees.size() == 0)
            idGenerator = new AtomicLong(0);
        else {
            long currentEndId = employees.get(employees.size() - 1).getId();
            idGenerator = new AtomicLong(currentEndId);
        }
    }


    @Override
    public Employee create(Employee employee) {
        employee.setId(idGenerator.incrementAndGet());
        List<Employee> employees = getAll();

        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee update(Long id, Employee employeeToUpdate) {
        List<Employee> employees = getAll();
        employeeToUpdate.setId(id);

        for (int i = 0; i < employees.size(); i++) {
            if (id.equals(employees.get(i).getId())) {
                employees.set(i, employeeToUpdate);
            }
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeToUpdate;
    }

    @Override
    public Optional<Employee> getById(Long id) {
        List<Employee> employees = getAll();

        if (employees == null) {
            return Optional.empty();
        }
        return employees.stream()
                .filter(employee -> id.equals(employee.getId()))
                .findFirst();
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(file))) {

            employees = (List<Employee>) inputStream.readObject();
        } catch (NoSuchFileException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void deleteById(Long id) {
        List<Employee> employees = getAll();

        if (employees == null) {
            return;
        }

        employees = employees.stream()
                .filter(employee -> !id.equals(employee.getId()))
                .collect(Collectors.toList());

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
