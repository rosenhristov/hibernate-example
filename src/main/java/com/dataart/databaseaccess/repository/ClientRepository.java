package com.dataart.databaseaccess.repository;

import com.dataart.databaseaccess.model.Client;
import com.dataart.databaseaccess.model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ClientRepository {

    private SessionFactory factory;

    public ClientRepository(SessionFactory factory) {
        this.factory = factory;
    }

    public Long add(String name, String surname) {
        Transaction tx = null;
        Long clientID = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            Client client = new Client(name, surname);
            clientID = (Long) session.save(client);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Could not save client " + name + " " + surname +  "to database.\n");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return clientID;
    }

    /* Method to UPDATE AccountManager of a client */
    public void updateClientAccountManager(int clientId, int accManId) {
        Transaction tx = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            Client client = session.get(Client.class, (long) clientId);
            Employee accManager = session.get(Employee.class, (long) accManId);
            accManager.addClient(client);
            session.update(client);
            session.update(accManager);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Updating client " + clientId + "'s account manager failed:");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void delete(int clientID){
        Transaction tx = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            Client client = session.get(Client.class, (long) clientID);
            Employee accountManager = client.getAccountManager();
            client.removeAccountManager();
            if (accountManager != null) {
                session.update(accountManager);
            }
            session.delete(client);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Deleting client with id " + clientID + " failed.\n");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to  READ all the clients */
    public void listAll() {
        Transaction tx = null;
        Session session = factory.openSession();
        try {
            tx = session.beginTransaction();
            List<Client> clients = session.createQuery("FROM client", Client.class).list();
            clients.forEach(client -> System.out.println(client.toString()));
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            System.out.println("Retrieving clients failed.\n");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
