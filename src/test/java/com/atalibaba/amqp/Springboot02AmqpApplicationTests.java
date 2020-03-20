package com.atalibaba.amqp;

import com.atalibaba.amqp.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot02AmqpApplicationTests {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 1.单播（点对点）
     */
    @Test
    public void contextLoads() {
        //Message需要自己构造一个；定义消息体内容和消息头
     //   rabbitTemplate.send(exchange,routeKey,message);


        //object默认当做消息体的，只需要传入要发送的对象，自动序列化发给rabbitmq
        //  rabbitTemplate.convertAndSend(exchange,routeKey,object);

        Map<String,Object> map =new HashMap<>();
        map.put("msg","这是第一个消息");
        map.put("data", Arrays.asList("helloworld",123,true));


        //对象被默认序列化以后发送
    //     rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",map);
        rabbitTemplate.convertAndSend("exchange.direct","atguigu.news",new Book("西游记","吴承恩"));
    }

    //接受消息数据
    //class java.util.HashMap
    //{msg=这是第一个消息, data=[helloworld, 123, true]}
    //接受数据，如何将数据转化为json发送出去
    @Test
    public void  receive(){
        Object o = rabbitTemplate.receiveAndConvert("atguigu.news");  //对象可以强转
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /**
     * 广播的方式
     */
    @Test
    public  void sendMsgAll(){

        rabbitTemplate.convertAndSend("exchange.fanout","",new Book("红楼梦梦","曹雪芹经"));
    }


    @Test
    public void createExchange(){
        //创建exchange
      //  amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange")); //默认的direct的，durable:true 可以持久化的
      //  System.out.println("创建完成了。。");


        //创建队列
       //   amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true)); //可以持久化的队列

        //创建绑定规则 将exchange和Queue绑定  public Binding(String destination, Binding.DestinationType destinationType, String exchange,
        // String routingKey, Map<String, Object> arguments) {
     //   amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,"amqpadmin.exchange","amqp.hahaha",null));


        //删除操作
     //   amqpAdmin.deleteExchange("amqpadmin.exchange");
        amqpAdmin.deleteQueue("amqpadmin.queue");
    }


}
