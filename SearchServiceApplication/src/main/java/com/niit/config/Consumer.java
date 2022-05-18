package com.niit.config;
import com.niit.exception.MovieNotFoundException;
import com.niit.service.SearchServiceImpl;
import com.rabbitmq.domain.SearchDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @Autowired
    private SearchServiceImpl searchService;

    @RabbitListener(queues="search_by_key_queue")
    public  Object sendMovieListByKeword(String key) throws MovieNotFoundException {
        return searchService.searchAllMoviesBasedOnKeyword(key);
    }
    @RabbitListener(queues="search_by_keyAndGenre_queue")
    public  Object sendMovieListByKewordAndGenre(SearchDTO searchDTO) throws MovieNotFoundException {
        return searchService.searchAllMoviesBasedOnKeywordAndGenre(searchDTO.getKey(), searchDTO.getGenre());
    }
}
