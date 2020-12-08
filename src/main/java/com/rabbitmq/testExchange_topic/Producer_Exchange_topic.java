package com.rabbitmq.testExchange_topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer_Exchange_topic {
    public static void main(String args[]) throws Exception{
        //1 创建一个ConnectionFactiry
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2创建Connection
        Connection connection=connectionFactory.newConnection();

        //3 创建Channel
        Channel channel=connection.createChannel();

        //4 声明
        String exchangeName="test_topic_exchange";
        String routingKey1="user.save";
        String routingKey2="user.update";
        String routingKey3="user.delete.abs";

        //5 发送
        String msg1="hello world topic exchange message：测试topic类型的exchange ";
        channel.basicPublish(exchangeName,routingKey1,null,msg1.getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,msg1.getBytes());
        channel.basicPublish(exchangeName,routingKey3,null,msg1.getBytes());
        channel.close();
        connection.close();
    }
}
