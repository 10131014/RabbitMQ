package com.rabbitmq.testConfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer_Confirm {
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

        String exchangeNmae="test_confirm_exchange";
        String routingKey="aaaconfirm_save1";
        String queueName="test_confirm_queue";

        //声明队列和交换机,声明绑定，指定路由key
        channel.exchangeDeclare(exchangeNmae,"topic",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeNmae,routingKey);

        QueueingConsumer consumer=new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);

        while (true){
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            String msg_confirm=new String(delivery.getBody());
            System.err.println(msg_confirm);
        }





    }
}
