package com.niit.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetFavGenresClient {
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("getFavGenresDirectExchange")
    private DirectExchange exchange;

    @Autowired
    public GetFavGenresClient(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object getFavouritesGenresByMailRabbit(String email) {
        Object o = rabbitTemplate.convertSendAndReceive(exchange.getName(), "get_fav_genres_routing", email);
        System.out.println("received from server inside get" + o);
        return o;
    }

}
