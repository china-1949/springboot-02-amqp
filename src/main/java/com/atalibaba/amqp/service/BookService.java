package com.atalibaba.amqp.service;

import com.atalibaba.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @RabbitListener(queues ="atguigu.news" )
    public void  receive(Book book){
        System.out.println("收到消息为："+book);
    }

    @RabbitListener(queues = "atguigu")
    public void receive02(Message message){
        System.out.println("message.getBody()"+message.getBody());
        System.out.println("message.getMessageProperties()"+message.getMessageProperties());
    }
}
