package com.rabbitmq.testMssage;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Producer_message {
    public static void main(String[] args) throws Exception{
        //1 创建一个ConnectionFactory，并进行配置
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2 通过连接工厂创建连接
        Connection connection=connectionFactory.newConnection();

        //3 通过connection创建一个Channel
        Channel channel=connection.createChannel();

        Map<String,Object> headers=new HashMap<>();
        headers.put("myName","石剑钧");
        headers.put("myAge",24);

        AMQP.BasicProperties properties=new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("50000")
                .headers(headers)
                .build();


        //4 通过Channel发送数据
        for (int i=0;i<5;i++)
        {
            String msg="hello rabbitmq";
            //1 exchang     2 routingkey
            channel.basicPublish("","test001",properties,msg.getBytes());
        }

        //5 关闭相关连接
        channel.close();
        connection.close();
    }
}
