package com.rabbitmq.testMssage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.Map;

public class Cosumer_message {
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

        //4 声明一个队列
        String queuename="test001";
        channel.queueDeclare(queuename,true,false,false,null);

        //5 创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);

        //6 设置Channel
        channel.basicConsume(queuename,true,queueingConsumer);
        while (true)
        {
            //7 获取消息
            QueueingConsumer.Delivery delivery=queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.err.println("消费端已收到："+msg);
            Map<String,Object> headers = delivery.getProperties().getHeaders();
            System.err.println("headers get myName："+headers.getOrDefault("myName",0));
        }
    }
}
