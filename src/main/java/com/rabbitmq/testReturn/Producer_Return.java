package com.rabbitmq.testReturn;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Producer_Return {
    public static void main(String args[]) throws Exception{
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setVirtualHost("/");
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();

        String exchangeName="test_return_exchange";
        String exchangeNameerr="test_return_exchange2";
        String routingKey="return.save";
        String routingKeyError="abc.save";

        String msg="test rabbitmq return masssge";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.err.println("-------handel return----");
                System.err.println("replyCode:"+i);
                System.err.println("replyText:"+s);
                System.err.println("exchange:"+s1);
                System.err.println("routingKey:"+s2);
                System.err.println("properties:"+basicProperties);
                System.err.println("boby:"+new String(bytes));
            }
        });
        channel.basicPublish(exchangeName,routingKeyError,true,null,msg.getBytes());


    }
}
