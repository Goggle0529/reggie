package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;

import java.util.List;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);

    List<OrderDetail> getOrderDetailListByOrderId(Long orderId);

}
