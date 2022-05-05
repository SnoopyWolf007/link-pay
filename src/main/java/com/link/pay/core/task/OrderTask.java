package com.link.pay.core.task;

import com.link.pay.core.entity.Order;
import com.link.pay.core.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class OrderTask {

    private final OrderService orderService;

    @Scheduled(fixedDelay = 1000)
    public void refreshExpireTime() {
        List<Order> payExpireList = orderService.findPayExpireList();
        for (Order o : payExpireList) {
            o.setStatus(2);
        }
        orderService.updateBatchById(payExpireList);
    }

}
