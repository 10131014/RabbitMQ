package com.rabbitmq.test1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
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

        String exchangeName="test_consumer_exchange";
        String routingKey="consumer.save";

        //4 通过Channel发送数据
        for (int i=0;i<5;i++)
        {
            String msg="hello rabbitmq";
            //1 exchang     2 routingkey
            channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
        }

        //5 关闭相关连接
        //channel.close();
        //connection.close();
    }
}
