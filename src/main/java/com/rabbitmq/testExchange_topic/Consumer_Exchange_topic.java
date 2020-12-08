package com.rabbitmq.testExchange_topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer_Exchange_topic {
    public static void main(String args[]) throws Exception{
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setPort(5672);
        connectionFactory.setHost("localhost");
        connectionFactory.setVirtualHost("/");

        Connection connection=connectionFactory.newConnection();

        Channel channel=connection.createChannel();

        String exchangeName="test_topic_exchange";
        String exchangeType="topic";
        String queueName="test_topic_queue";
        String routingKey="user.*";

        //声明交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        //声明队列
        channel.queueDeclare(queueName,false,false,false,null);
        //声明绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);

        while (true)
        {
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String msg_topic=new String(delivery.getBody());
            System.out.println("消费端收到消息："+msg_topic);
        }
    }
}
