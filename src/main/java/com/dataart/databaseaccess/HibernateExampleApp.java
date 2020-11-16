package com.dataart.databaseaccess;

import com.dataart.databaseaccess.repository.ClientRepository;
import com.dataart.databaseaccess.repository.EmployeeRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.math.BigDecimal;

public class HibernateExampleApp {

    private static SessionFactory factory;

    public static void main(String[] args) {

        try {
            factory = buildSessionFactory();

            ClientRepository clientRepo = new ClientRepository(factory);
            EmployeeRepository empRepo = new EmployeeRepository(factory);

            /* Add few employee records in database */
            Long empID1 = empRepo.add("Iskra", "Dobreva", BigDecimal.valueOf(5000));
            Long empID2 = empRepo.add("Anna", "Petrova", BigDecimal.valueOf(4500));
            Long empID3 = empRepo.add("Iva", "Dobreva", BigDecimal.valueOf(4900));
            Long empID4 = empRepo.add("Elena", "Veselinova", BigDecimal.valueOf(4800));

            /* Add few customer records in database */
            Long clientID1 = clientRepo.add("Zara", "Allen");
            Long clientID2 = clientRepo.add("Daisy", "Das");
            Long clientID3 = clientRepo.add("John", "Doe");
            Long clientID4 = clientRepo.add("Ron", "Moss");
            Long clientID5 = clientRepo.add("Gary", "Cooper");
            Long clientID6 = clientRepo.add("Jan", "Rogers");
            Long clientID7 = clientRepo.add("Aliona", "Belova");
            Long clientID8 = clientRepo.add("Karen", "Lou");
            Long clientID9 = clientRepo.add("Ross", "Stone");
            Long clientID10 = clientRepo.add("Raya", "Kavalova");
            Long clientID11 = clientRepo.add("Maya", "Petrov");

          /* Update employee's records */
            empRepo.updateEmployeeClients(1, 1);
            empRepo.updateEmployeeClients(1, 2);
            empRepo.updateEmployeeClients(1, 3);
            empRepo.updateEmployeeClients(2, 4);
            empRepo.updateEmployeeClients(2, 5);
            empRepo.updateEmployeeClients(2, 6);

            /* Update Client's records */
            clientRepo.updateClientAccountManager(7, 3);
            clientRepo.updateClientAccountManager(8, 3);
            clientRepo.updateClientAccountManager(9, 3);
            clientRepo.updateClientAccountManager(10, 3);

            /* Delete an employee from the database */
            empRepo.delete(2);

            /* Delete an client from the database */
            clientRepo.delete(11);

            /* List down new list of the clients */
            empRepo.listAll();

            /* List down new list of the clients */
            clientRepo.listAll();

        } catch (Exception ex) {
            System.err.println("Failed to create sessionFactory object.\n");
            ex.printStackTrace();
        } finally {
            if (factory != null){
                factory.close();
            }
        }
    }

    private static SessionFactory buildSessionFactory() throws IOException {
        return new Configuration()
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgresPlusDialect")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url",
                             "jdbc:postgresql://localhost:5432/hibernate_example?currentSchema=public")
                .setProperty("hibernate.hbm2ddl.auto", "none")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "7840")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("current_session_context_class", "thread")
                .addAnnotatedClass(com.dataart.databaseaccess.model.Employee.class)
                .addAnnotatedClass(com.dataart.databaseaccess.model.Client.class)
                .buildSessionFactory();
    }
}
