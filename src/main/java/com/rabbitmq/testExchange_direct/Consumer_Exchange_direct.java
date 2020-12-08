package com.rabbitmq.testExchange_direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer_Exchange_direct {
    public static void main(String args[]) throws Exception{
        //1 创建ConnectionFactory
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        //2 创建connection连接
        Connection connection=connectionFactory.newConnection();

        //3 创建Channel
        Channel channel=connection.createChannel();

        //4 声明
        String exchangeName="test_direct_exchange";
        String exchangeType="direct";
        String queueName="test_direct_queue";
        String routingKey="test.direct";
        //声明了一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        //声明了一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        //声明了一个绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);

        //durable 是否可持久化
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //参数：队列名称，是否自动ACK，consumer
        channel.basicConsume(queueName,true,consumer);

        while (true)
        {
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String msg1=new String(delivery.getBody());
            System.out.println("消费端收到消息："+msg1);
        }
    }
}
