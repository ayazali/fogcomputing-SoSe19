package de.fc.projekt.comm;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Component
public class RedisConnector {

    private String RD_HOSTNAME;
    private int RD_PORT;

    private boolean isConnected = false;

    private Jedis jedis;

    public RedisConnector(String qhostname, int qport) {
        this.RD_HOSTNAME = qhostname;
        this.RD_PORT = qport;
    }

    public boolean connect() {
        this.jedis = new Jedis(this.RD_HOSTNAME, this.RD_PORT);
        return this.isConnected = true;
    }

    public boolean post(JsonObject jsonObject) {

        this.jedis.sadd("tinkerforge-cache", jsonObject.toString());
        System.out.println(" [x] Sent to cache'" + jsonObject.toString() + "'");
        return true;
    }

    public List<JsonObject> getValues() {
        Set<String> allCache = this.jedis.smembers("tinkerforge-cache");
        this.jedis.del("tinkerforge-cache");

        List<JsonObject> listOfJsonObjects = new ArrayList<>();
        // Iterator to traverse the list
        Iterator iterator = allCache.iterator();
        Gson jsonParser = new Gson();

        while (iterator.hasNext()) {
            String stringJson = (String) iterator.next();
            JsonObject tmpObj = jsonParser.fromJson(stringJson, JsonObject.class);
            listOfJsonObjects.add(tmpObj);
        }

        return listOfJsonObjects;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
