package com.niit.config;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component
public class DeleteAllFavouriteClient {
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("deleteAllFavDirectExchange")
    private DirectExchange exchange;
    @Autowired
    public DeleteAllFavouriteClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object deleteFavouriteByEmail(String email)
    {
        Object o = rabbitTemplate.convertSendAndReceive(exchange.getName(),"del_all_fav_routing",email);
        System.out.println("received from server inside get ->"+o);
        return o;
    }
}
