package com.link.pay.core.manager;

import com.link.pay.core.entity.Order;
import com.link.pay.core.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Component
@AllArgsConstructor
public class OrderManager {

    private final static ArrayBlockingQueue<Order> queue = new ArrayBlockingQueue<>(5);

    private final OrderService orderService;

    @PostConstruct
    public synchronized void load() {
        List<Order> orderList = orderService.findByPaying();
        queue.addAll(orderList);
    }

    /**
     * 取出。没有元素的时候返回null
     */
    public synchronized Order take() {
        return queue.poll();
    }

    /**
     * 在超出容量时，返回false
     */
    public synchronized boolean add(Order order) {
        return queue.offer(order);
    }

}
