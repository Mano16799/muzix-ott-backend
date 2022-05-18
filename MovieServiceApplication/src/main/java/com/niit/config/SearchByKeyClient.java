package com.niit.config;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchByKeyClient {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("getSearchByKeyDirectExchange")
    private DirectExchange exchange;

    @Autowired
    public SearchByKeyClient(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public Object searchMoviesByKeyRabbit(String key)
    {
        Object obj=rabbitTemplate.convertSendAndReceive(exchange.getName(),"get_searchByKey_routing",key);
        System.out.println("search result"+obj);
        return obj;
    }

}
