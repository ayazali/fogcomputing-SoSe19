package de.fc.projekt.comm;

import com.google.gson.JsonObject;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public class ZeroMQConnector {

    private ZContext context;
    private  ZMQ.Socket publisher;

    private String hostname;
    private Integer port;

    public ZeroMQConnector(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void connect(){

        this.context = new ZContext();
        this.context.isClosed();
        this.publisher = this.context.createSocket(SocketType.PUB);
        Boolean isConnected = this.publisher.bind("tcp://"+ this.hostname +":" + this.port);
        if (isConnected) {
            System.out.println("Connection to zmq successful");
        } else {
            System.out.println("Connection failed to zmq");
        }
    }

    public boolean post(JsonObject jsonObject){
        this.publisher.send(jsonObject.toString());
        return this.publisher.sendMore(jsonObject.toString());
    }

    public void disconnect(){
        this.publisher.disconnect("tcp://"+ this.hostname +":" + this.port);
    }

}
