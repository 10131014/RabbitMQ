package com.rabbitmq.testConfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Producer_Confirm {
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

        //指定消息投递模式：消息的确认模式
        channel.confirmSelect();
        String exchangeNmae="test_confirm_exchange";
        String routingKey="aaaconfirm_save1";

        //4 通过Channel发送数据

            String msg="hello rabbitmq send confirm message";
            //1 exchang     2 routingkey
            channel.basicPublish(exchangeNmae,routingKey,null,msg.getBytes());
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long l, boolean b) throws IOException {
                    System.err.println("-------ack!-----");
                }

                @Override
                public void handleNack(long l, boolean b) throws IOException {
                    System.err.println("----no ack!-----");

                }
            });


        //5 关闭相关连接
        //channel.close();
        //connection.close();
    }
}
