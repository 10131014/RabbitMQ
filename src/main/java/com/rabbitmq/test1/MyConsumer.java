package com.rabbitmq.test1;

import com.rabbitmq.client.*;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {
    public MyConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        //super.handleDelivery(consumerTag, envelope, properties, body);
        System.err.println("-----consumer message--------");
        System.err.println("consumerTag:"+consumerTag);
        System.err.println("envelope:"+envelope);
        System.err.println("properties:"+properties);
        System.err.println("body:"+new String(body));
    }
}
