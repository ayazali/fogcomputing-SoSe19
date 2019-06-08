package de.fc.projekt.webnode;

import de.fc.projekt.webnode.comm.RabbitMQConnector;
import de.fc.projekt.webnode.models.MQEntry;
import de.fc.projekt.webnode.repositories.MQEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    MQEntryRepository mqEntryRepository;

    @PostConstruct
    public void addQueueListeners() {
        MQEntry entry_eins = new MQEntry("192.168.178.46", 5672, "guest", "guest", "ParkenEtage");
        this.mqEntryRepository.save(entry_eins);

        RabbitMQConnector rabbitMQConnector = new RabbitMQConnector(entry_eins.getQueueName(), entry_eins.getHostname(),
                entry_eins.getPort(), entry_eins.getMqUsername(), entry_eins.getMqPassword());

        try {
            rabbitMQConnector.connect();
            rabbitMQConnector.startListener();
            System.out.println("Connected to rabbitMQ");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
