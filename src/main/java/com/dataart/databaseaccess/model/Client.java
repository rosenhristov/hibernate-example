package com.dataart.databaseaccess.model;

import javax.persistence.*;

@Entity
@Table(name = "client", schema = "public")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long clientId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="employee_id")
    private Employee accountManager;

    public Client() {
    }

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public Employee getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(Employee accountManager) {
        this.accountManager = accountManager;
    }

    public void addAccountManager(Employee accountManager) {
        accountManager.addClient(this);
    }

    public void removeAccountManager() {
        if (accountManager == null) {
            return;
        }
        accountManager.getClients().remove(this);
        setAccountManager(null);
    }



    @Override
    public String toString() {
        return new StringBuilder("Client{")
                .append("id: '").append(clientId)
                .append(", name: '").append(name)
                .append(", surname: '").append(surname)
                .append('}')
                .toString();
    }
}