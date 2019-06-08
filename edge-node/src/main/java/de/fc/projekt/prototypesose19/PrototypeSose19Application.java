package de.fc.projekt.prototypesose19;

import de.fc.projekt.comm.RabbitMQConnector;
import de.fc.projekt.comm.RedisConnector;
import de.fc.projekt.tinkerforge.TinkerForgeConnector;
import de.fc.projekt.tinkerforge.TinkerForgeConnectorMockImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@ComponentScan(value = "de.fc.projekt")
public class PrototypeSose19Application {

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

    @Value("${redis.hostname}")
    private String RD_HOSTNAME;
    @Value("${redis.port}")
    private int RD_PORT;

    public static void main(String[] args) {

        SpringApplication.run(PrototypeSose19Application.class, args);
    }

    @PostConstruct
    public void init() {
        // start your monitoring in here
        TinkerForgeConnector tinkerForgeConnector = null;

        try {
            tinkerForgeConnector = new TinkerForgeConnectorMockImpl();
            tinkerForgeConnector.setRabbitMQConnector(new RabbitMQConnector(QUEUE_NAME, MQ_HOSTNAME, MQ_PORT, MQ_USERNAME, MQ_PASSWORD));
            tinkerForgeConnector.setRedisConnector(new RedisConnector(RD_HOSTNAME, RD_PORT));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            try {
                Thread.sleep(5000);
                tinkerForgeConnector.connect("0.0.0.0", 4223, "GU3");
                int max = 120;
                int min = 30;
                int random = (int) (Math.random() * max + min);
                boolean _resp = tinkerForgeConnector.persistSensorReading(random);

                if (_resp) {
                    System.out.println("Value successfully posted to rabbit-mq");
                } else {
                    System.out.println("Value not posted to rabbit-mq");
                }

            } catch (Exception ex) {
                System.out.println(ex.toString());
                tinkerForgeConnector.startActiveCaching();
            }

        }
    }


}
