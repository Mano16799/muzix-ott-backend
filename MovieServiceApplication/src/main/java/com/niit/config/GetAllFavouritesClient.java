package com.niit.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetAllFavouritesClient {
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("getFavDirectExchange")
    private DirectExchange exchange;

    @Autowired
    public GetAllFavouritesClient(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object getFavouritesByMailRabbit(String email)
    {
        Object o = rabbitTemplate.convertSendAndReceive(exchange.getName(),"get_fav_routing",email);
        System.out.println("received from server inside get"+o);
        return o;
    }
}
