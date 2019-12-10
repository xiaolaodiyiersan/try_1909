package com.qf.Listent;

import com.qf.entity.goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Rabbit监听器
 */
@Component
public class RabbitMQListent {
    @Autowired
    ISearchService searchService;
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "good_exchange",type = "fanout"),
            value = @Queue(name = "search_queue")
    ))
    public void msgHandler(goods good){
      //  System.out.println("接收到："+ good);
        searchService.insert(good);
    }
}
