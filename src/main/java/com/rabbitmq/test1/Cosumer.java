package com.rabbitmq.test1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Cosumer {
    public static void main(String args[]) throws Exception{
        //1 创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection=connectionFactory.newConnection();

        //3 通过connection创建一个Channel
        Channel channel=connection.createChannel();

        String exchangeName="test_consumer_exchange";
        String rountingKey="consumer.#";
        String queuename="test_consumer_queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queuename,true,false,false,null);
        channel.queueBind(queuename,exchangeName,rountingKey);


        //6 设置Channel
        channel.basicConsume(queuename,true,new MyConsumer(channel));


    }
}
