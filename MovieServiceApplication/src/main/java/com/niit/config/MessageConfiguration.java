package com.niit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {

    //save favourite configurations**********************
    private String exchangeName="fav_exchange";
    private String registerQueue="fav_queue";

    @Bean
    public DirectExchange directExchange()
    {
        return new DirectExchange(exchangeName);
    }


    @Bean
    public Queue registerQueue()
    {
        return new Queue(registerQueue,true);
    }


    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter()
    {
        return new  Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemp=new RabbitTemplate(connectionFactory);
        rabbitTemp.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemp;
    }

    @Bean
    Binding binding(@Qualifier("registerQueue")Queue registerQueue,@Qualifier("directExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(registerQueue()).to(exchange).with("fav_routing");
    }

    //get all favourites configurations*********************
    private String getFavExchangeName="get_fav_exchange";
    private String getFavRegisterQueue="get_fav_queue";

    @Bean
    public DirectExchange getFavDirectExchange()
    {
        return new DirectExchange(getFavExchangeName);
    }

    @Bean
    public Queue getFavRegisterQueue()
    {
        return new Queue(getFavRegisterQueue,false);
    }

    @Bean
    Binding getFavBinding(@Qualifier("getFavRegisterQueue")Queue registerQueue,@Qualifier("getFavDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(getFavRegisterQueue()).to(exchange).with("get_fav_routing");
    }

    //delete favourite configuration
    private String deleteFavExchangeName="del_fav_exchange";
    private String deleteFavRegisterQueue="del_fav_queue";

    @Bean
    public DirectExchange deleteFavDirectExchange()
    {
        return new DirectExchange(deleteFavExchangeName);
    }

    @Bean
    public Queue deleteFavRegisterQueue()
    {
        return new Queue(deleteFavRegisterQueue,false);
    }

    @Bean
    Binding deleteFavBinding(@Qualifier("deleteFavRegisterQueue")Queue registerQueue,@Qualifier("deleteFavDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(deleteFavRegisterQueue()).to(exchange).with("del_fav_routing");
    }

    //get favourite genres configuration
    private String getFavGenresExchangeName="get_fav_genres_exchange";
    private String getFavGenresRegisterQueue="get_fav_genres_queue";

    @Bean
    public DirectExchange getFavGenresDirectExchange()
    {
        return new DirectExchange(getFavGenresExchangeName);
    }

    @Bean
    public Queue getFavGenresRegisterQueue()
    {
        return new Queue(getFavGenresRegisterQueue,false);
    }

    @Bean
    Binding getFavGenresBinding(@Qualifier("getFavGenresRegisterQueue")Queue registerQueue,@Qualifier("getFavGenresDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(registerQueue).to(exchange).with("get_fav_genres_routing");
    }

    //delete ALL favourite configuration
    private String deleteAllFavExchangeName="del_all_fav_exchange";
    private String deleteAllFavRegisterQueue="del_all_fav_queue";

    @Bean
    public DirectExchange deleteAllFavDirectExchange()
    {
        return new DirectExchange(deleteAllFavExchangeName);
    }

    @Bean
    public Queue deleteAllFavRegisterQueue()
    {
        return new Queue(deleteAllFavRegisterQueue,false);
    }

    @Bean
    Binding deleteAllFavBinding(@Qualifier("deleteAllFavRegisterQueue")Queue registerQueue,@Qualifier("deleteAllFavDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(deleteAllFavRegisterQueue()).to(exchange).with("del_all_fav_routing");
    }


    //************ get search result by keyword  congiguration **********
    private String getSearchByKeyExchangeName="search_by_key_exchange";
    private String getSearchByKeyRegisterQueue="search_by_key_queue";
    @Bean
    public DirectExchange getSearchByKeyDirectExchange()
    {
        return new DirectExchange(getSearchByKeyExchangeName);
    }
    @Bean
    public Queue getSearchByKeyRegisteredQueue()
    {
        return new Queue(getSearchByKeyRegisterQueue,false);
    }
    @Bean
    Binding getSearchByKeyBinding(@Qualifier("getSearchByKeyRegisteredQueue")Queue registerQueue,@Qualifier("getSearchByKeyDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(getSearchByKeyRegisteredQueue()).to(exchange).with("get_searchByKey_routing");
    }

    //************ get search result by keyword And Genre congiguration **********
    private String getSearchByKeyAndGenreExchangeName="search_by_keyAndGenre_exchange";
    private String getSearchByKeyAndGenreRegisterQueue="search_by_keyAndGenre_queue";
    @Bean
    public DirectExchange getSearchByKeyAndGenreDirectExchange()
    {
        return new DirectExchange(getSearchByKeyAndGenreExchangeName);
    }
    @Bean
    public Queue getSearchByKeyAndGenreRegisteredQueue()
    {
        return new Queue(getSearchByKeyAndGenreRegisterQueue,true);
    }
    @Bean
    Binding getSearchByKeyAndGenreBinding(@Qualifier("getSearchByKeyAndGenreRegisteredQueue")Queue registerQueue,@Qualifier("getSearchByKeyAndGenreDirectExchange") DirectExchange exchange)
    {
        return BindingBuilder.bind(getSearchByKeyAndGenreRegisteredQueue()).to(exchange).with("get_searchByKeyAndGenre_routing");
    }



}
