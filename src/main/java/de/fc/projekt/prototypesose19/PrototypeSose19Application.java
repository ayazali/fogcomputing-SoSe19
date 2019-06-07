package de.fc.projekt.prototypesose19;

import de.fc.projekt.tinkerforge.TinkerForgeConnector;
import de.fc.projekt.tinkerforge.TinkerForgeConnectorMockImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrototypeSose19Application {

	public static void main(String[] args) {

		SpringApplication.run(PrototypeSose19Application.class, args);
		TinkerForgeConnector tinkerForgeConnector = new TinkerForgeConnectorMockImpl();

		while(true){
			try{
				Thread.sleep(5000);
				tinkerForgeConnector.connect("0.0.0.0", 4223, "GU3");
				int max = 120;
				int min = 30;
				int random = (int )(Math.random() * max + min);
				Boolean _resp = tinkerForgeConnector.persistSensorReading(random);

				if (_resp) {
					System.out.println("Value successfully posted to zmq");
				} else {
					System.out.println("Value not posted to zmq");
				}

			} catch (Exception ex){
				System.out.println(ex.toString());
			}

		}
	}

}
