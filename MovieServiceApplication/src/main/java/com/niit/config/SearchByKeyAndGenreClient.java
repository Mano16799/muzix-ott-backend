package com.niit.config;


import com.rabbitmq.domain.SearchDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchByKeyAndGenreClient {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("getSearchByKeyAndGenreDirectExchange")
    private DirectExchange exchange;

    @Autowired
    public SearchByKeyAndGenreClient(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object getSearchByKeyAndGenreResultRabbit(SearchDTO searchDTO)
    {
        Object obj=rabbitTemplate.convertSendAndReceive(exchange.getName(),"get_searchByKeyAndGenre_routing",searchDTO);
        System.out.println("search by key and genre"+obj);
        return obj;
    }
}
