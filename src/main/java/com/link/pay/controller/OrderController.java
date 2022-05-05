package com.link.pay.controller;

import com.link.pay.core.annotation.WhiteApi;
import com.link.pay.core.entity.Order;
import com.link.pay.core.manager.OrderManager;
import com.link.pay.core.model.IRequest;
import com.link.pay.core.model.IResponse;
import com.link.pay.core.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderManager orderManager;

    @WhiteApi
    @RequestMapping("/pull")
    public IResponse pull(@RequestBody IRequest<Order> iRequest) {
        Order take = orderManager.take();
        return IResponse.success().setData(take);
    }

    @WhiteApi
    @RequestMapping("/qr")
    public IResponse qr(@RequestBody IRequest<Order> iRequest) {
        Order body = iRequest.getBody();
        String orderNo = body.getOrderNo();
        String qr = body.getQr();
        orderService.updateQrByOrderNo(orderNo, qr);
        return IResponse.success();
    }

    @WhiteApi
    @RequestMapping("/push")
    public IResponse push(@RequestBody IRequest<Order> iRequest) {
        Order body = iRequest.getBody();
        String orderNo = body.getOrderNo();
        orderService.updateStatusByOrderNo(orderNo, 1);
        return IResponse.success();
    }

    @WhiteApi
    @RequestMapping("/cancel")
    public IResponse cancel(@RequestBody IRequest<Order> iRequest) {
        Order body = iRequest.getBody();
        String orderNo = body.getOrderNo();
        orderService.updateStatusByOrderNo(orderNo, 3);
        return IResponse.success();
    }

    @WhiteApi
    @RequestMapping("/create")
    public IResponse create(@RequestBody IRequest<Order> iRequest) {
        Order body = iRequest.getBody();
        String orderNo = UUID.randomUUID().toString().replaceAll("-", "");
        body.setOrderNo(orderNo);
        body.setStatus(0);
        // 2分钟 60
        body.setPayExpireTime(System.currentTimeMillis() + 120000);
        orderService.save(body);
        orderManager.add(body);

        // TODO: 后面修改获取二维码成功之后才能创建订单

        return IResponse.success().setData(body);
    }

    @WhiteApi
    @RequestMapping("/get")
    public IResponse get(@RequestBody IRequest<Order> iRequest) {
        Order order = orderService.getById(iRequest.getBody().getId());
        return IResponse.success().setData(order);
    }

}
