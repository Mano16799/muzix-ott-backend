package com.niit.config;

import com.rabbitmq.domain.FavouriteDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PostFavouriteClient {
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("directExchange")
    private DirectExchange exchange;

    @Autowired
    public PostFavouriteClient(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object saveFavouriteRabbit(FavouriteDTO favouriteDTO)
    {
        Object o = rabbitTemplate.convertSendAndReceive(exchange.getName(),"fav_routing",favouriteDTO);
        System.out.println("received from server inside post "+o);
        return o;
    }

}