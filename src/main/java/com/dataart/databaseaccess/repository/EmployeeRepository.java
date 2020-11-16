package com.dataart.databaseaccess.repository;

import com.dataart.databaseaccess.model.Client;
import com.dataart.databaseaccess.model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeRepository {

    private SessionFactory factory;

    public EmployeeRepository(SessionFactory factory) {
        this.factory = factory;
    }

    public Long add(String name, String surname, BigDecimal salary) {
        Transaction tx = null;
        Long employeeID = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(name, surname, salary);
            employeeID = (Long) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) {
                tx.rollback();
            }
            System.out.printf("Unable to add a new employee %s %s:\n", name, surname);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    // Method to UPDATE the list of clients the employee is responsible for as their account manager
    public void updateEmployeeClients(int employeeId, int clientId) {
        Transaction tx = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            Employee employee = session.get(Employee.class, (long) employeeId);
            Client client = session.get(Client.class, (long) clientId);
            employee.addClient(client);
            session.update(client);
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) {
                tx.rollback();
            }
            System.out.printf("Updating employee clients with client %s failed:\n", clientId);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from database */
    public void delete(int employeeID) {
        Transaction tx = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            Employee employee = session.get(Employee.class, (long) employeeID);
            employee.removeClients();
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.printf("Deleteing employee with id %s failed:\n", employeeID);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to READ all the employees */
    public void listAll() {
        Transaction tx = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            List<Employee> employees = session.createQuery("FROM employee").getResultList();
            if (employees == null) {
                tx.rollback();
                session.close();
                return;
            }
            employees.forEach(employee -> System.out.print(employee.toString()));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Retrieving employees failed:");
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void listClientsOfAccountManager(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();
        // get clients for a given employee
        Query<Client> query = session.createQuery(
                "select c from client c where c.AccountManager=:accManId", Client.class);
        query.setParameter("accManId", id);
        List<Client> clients = query.getResultList();
        clients.forEach(client -> System.out.println(client.toString()));
    }
}
