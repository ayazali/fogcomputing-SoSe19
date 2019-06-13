package de.fc.projekt.prototypesose19;

import de.fc.projekt.comm.RabbitMQConnector;
import de.fc.projekt.comm.RedisConnector;
import de.fc.projekt.tinkerforge.TinkerForgeConnector;
import de.fc.projekt.tinkerforge.TinkerForgeConnectorImpl;
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

    @Value("${device.id}")
    private String DEVICE_ID;

    @Value("${exec_mode}")
    private String EXEC_MODE_MOCK;

    public static void main(String[] args) {
        SpringApplication.run(PrototypeSose19Application.class, args);
    }

    @PostConstruct
    public void init() throws IOException {

        boolean EXEC_MODE_MOCK_BOOL = EXEC_MODE_MOCK.equals("mock");
        System.out.println("Executing with mode " + this.EXEC_MODE_MOCK);

        // start your monitoring in here
        TinkerForgeConnector tinkerForgeConnector = null;

        try {

            if (EXEC_MODE_MOCK_BOOL) {

                tinkerForgeConnector = new TinkerForgeConnectorMockImpl();
                tinkerForgeConnector.setDeviceId(DEVICE_ID);
                tinkerForgeConnector.setRabbitMQConnector(new RabbitMQConnector(QUEUE_NAME, MQ_HOSTNAME, MQ_PORT, MQ_USERNAME, MQ_PASSWORD));
                tinkerForgeConnector.setRedisConnector(new RedisConnector(RD_HOSTNAME, RD_PORT));
                tinkerForgeConnector.connect("0.0.0.0", 4223, "GU3");

                while (true) {
                    try {
                        Thread.sleep(5000);

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
            } else {

                tinkerForgeConnector = new TinkerForgeConnectorImpl();
                tinkerForgeConnector.setDeviceId(DEVICE_ID);
                tinkerForgeConnector.setRabbitMQConnector(new RabbitMQConnector(QUEUE_NAME, MQ_HOSTNAME, MQ_PORT, MQ_USERNAME, MQ_PASSWORD));
                tinkerForgeConnector.setRedisConnector(new RedisConnector(RD_HOSTNAME, RD_PORT));
                tinkerForgeConnector.connect("localhost", 4223, "GU3");
                System.out.println("Press key to exit");
                System.in.read();
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
