package com.dataart.databaseaccess.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "employee",schema = "public")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "salary")
    private BigDecimal salary;

    @OneToMany(fetch = FetchType.EAGER,
               mappedBy="accountManager",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<Client> clients;

    public Employee() {
    }

    public Employee(String name, String surname, BigDecimal salary) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<Client> getClients() {
        if (clients == null) {
            clients = new ArrayList<>();
        }
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        getClients().add(client);
        client.setAccountManager(this);
    }

    public void removeClient(Client client) {
        getClients().remove(client);
        client.setAccountManager(null);
    }

    public void removeClients(List<Client> clients) {
        this.getClients().removeAll(clients);
        clients.forEach(client -> client.setAccountManager(null));
    }

    public void removeClients() {
        clients.forEach(client -> client.setAccountManager(null));
        this.setClients(null);
    }


    @Override
    public String toString() {
        return new StringBuilder("Employee{")
                .append("id: '").append(employeeId)
                .append(", name: '").append(name).append('\'')
                .append(", surname: '").append(surname).append('\'')
                .append(", salary: '").append(salary)
                .append(", clients: '").append(Arrays.toString(clients.toArray()))
                .append('}')
                .toString();
    }
}