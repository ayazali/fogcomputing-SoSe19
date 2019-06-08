package de.fc.projekt.webnode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MQEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mqId;
    private String queueName;
    private String hostname;
    private int port;
    private String mqUsername;
    private String mqPassword;


    public MQEntry(String hostname, int port, String username, String password, String queueName) {
        this.setHostname(hostname);
        this.setPort(port);
        this.setMqUsername(username);
        this.setMqPassword(password);
        this.setQueueName(queueName);
    }

    public long getMqId() {
        return mqId;
    }

    public void setMqId(long mqId) {
        this.mqId = mqId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMqUsername() {
        return mqUsername;
    }

    public void setMqUsername(String mqUsername) {
        this.mqUsername = mqUsername;
    }

    public String getMqPassword() {
        return mqPassword;
    }

    public void setMqPassword(String mqPassword) {
        this.mqPassword = mqPassword;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
