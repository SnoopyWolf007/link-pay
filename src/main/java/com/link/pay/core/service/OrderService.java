package com.link.pay.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.link.pay.core.dao.OrderDAO;
import com.link.pay.core.entity.Order;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService extends ServiceImpl<OrderDAO, Order> {

    /**
     * 订单号查询数据
     */
    public Order getByOrderNo(String orderNo) {
        Order qrEntity = new Order();
        qrEntity.setOrderNo(orderNo);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(qrEntity);
        return getOne(queryWrapper);
    }

    /**
     * 订单号更新状态
     */
    public void updateStatusByOrderNo(String orderNo, Integer status) {
        Order qrEntity = new Order();
        qrEntity.setOrderNo(orderNo);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(qrEntity);

        Order upEntity = new Order();
        upEntity.setStatus(status);
        if (1 == status) {
            upEntity.setPayTime(new Date());
        }

        update(upEntity, queryWrapper);
    }

    /**
     * 订单号更新状态
     */
    public void updateQrByOrderNo(String orderNo, String qr) {
        Order qrEntity = new Order();
        qrEntity.setOrderNo(orderNo);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(qrEntity);

        Order upEntity = new Order();
        upEntity.setQr(qr);
        update(upEntity, queryWrapper);
    }

    /**
     * 获取待支付列表
     */
    public List<Order> findByPaying() {
        Order qrEntity = new Order();
        qrEntity.setStatus(0);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(qrEntity);
        queryWrapper.ge("pay_expire_time", System.currentTimeMillis());
        return list(queryWrapper);
    }

    /**
     * 获取超时列表
     */
    public List<Order> findPayExpireList() {
        Order qrEntity = new Order();
        qrEntity.setStatus(0);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(qrEntity);
        queryWrapper.le("pay_expire_time", System.currentTimeMillis());
        return list(queryWrapper);
    }

}
