package com.rabbitmq.testExchange_fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer_Exchange_fanout {
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
        String exchangeName="test_fanout_exchange";
        //String routingKey="test.direct";//fanout交换机不用routingkey

        //5 发送
        for (int i=0;i<10;i++) {
            String msg1 = "hello world direct exchange message：测试fanout类型的exchange "+i;
            channel.basicPublish(exchangeName, "", null, msg1.getBytes());
        }
        channel.close();
        connection.close();
    }
}
