package de.fc.projekt.webnode;

import de.fc.projekt.webnode.comm.RabbitMQConnector;
import de.fc.projekt.webnode.models.MQEntry;
import de.fc.projekt.webnode.repositories.MQEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class Application {

    @Value("${rabbitmq.qname}")
    private String QUEUE_NAME;
    @Value("${rabbitmq.hostname}")
    private String MQ_HOSTNAME;
    @Value("${rabbitmq.port}")
    private int MQ_PORT;
    @Value("${rabbitmq.username}")
    private String MQ_USERNAME;
    @Value("${rabbitmq.password}")
    private String MQ_PASSWORD;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    MQEntryRepository mqEntryRepository;
    @Autowired
    RabbitMQConnector rabbitMQConnector;

    @PostConstruct
    public void addQueueListeners() {
        MQEntry entry_eins = new MQEntry(MQ_HOSTNAME, MQ_PORT, MQ_USERNAME, MQ_PASSWORD, QUEUE_NAME);
        this.mqEntryRepository.save(entry_eins);

        rabbitMQConnector.setQUEUE_NAME(entry_eins.getQueueName())
                .setMQ_HOSTNAME(entry_eins.getHostname())
                .setMQ_PORT(entry_eins.getPort())
                .setMQ_USERNAME(entry_eins.getMqUsername())
                .setMQ_PASSWORD(entry_eins.getMqPassword());

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
