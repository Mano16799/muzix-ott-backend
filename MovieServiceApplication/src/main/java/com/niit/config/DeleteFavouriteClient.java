package com.niit.config;

import com.rabbitmq.domain.MovieToBeDeletedDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteFavouriteClient {
    private RabbitTemplate rabbitTemplate;
    @Autowired
    @Qualifier("deleteFavDirectExchange")
    private DirectExchange exchange;

    @Autowired
    public DeleteFavouriteClient(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object deleteFavourite(MovieToBeDeletedDTO details)
    {
        Object o = rabbitTemplate.convertSendAndReceive(exchange.getName(),"del_fav_routing",details);
        System.out.println("received from server inside get ->"+o);
        return o;
    }
}
