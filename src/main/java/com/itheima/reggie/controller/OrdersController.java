package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.OrderDto;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders);
        ordersService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page> page(@RequestParam Integer page, Integer pageSize, Long number, String beginTime, String endTime) {
        log.info("已接收Page请求");

        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(number != null, Orders::getNumber, number)
                .gt(StringUtils.isNotBlank(beginTime), Orders::getOrderTime, beginTime)
                .lt(StringUtils.isNotBlank(endTime), Orders::getOrderTime, endTime);
        ordersService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> status(@RequestBody Map<String,String> map) {
        String id = map.get("id");
        Long orderId = Long.parseLong(id);
        Integer status = Integer.parseInt(map.get("status"));

        if (orderId == null || status == null) {
            return R.error("传入信息非法");
        }

        Orders order = ordersService.getById(orderId);
        order.setStatus(status);
        ordersService.updateById(order);

        return R.success("订单状态修改成功");
    }

    @GetMapping("/userPage")
    public R<Page> page(int page, int pageSize) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrderDto> pageDto = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo, queryWrapper);


        // 对Dto进行属性赋值
//        List<Orders> records = pageInfo.getRecords();
//        List<OrderDto> dtoList = records.stream().map((item) -> {
//            OrderDto orderDto = new OrderDto();
//            Long orderId = item.getId();
//            List<OrderDetail> orderDetailList = ordersService.getOrderDetailListByOrderId(orderId);
//
//            BeanUtils.copyProperties(item, orderDto);//把订单对象的数据复制到orderDto中
//            //对orderDto进行OrderDetails属性的赋值
//            orderDto.setOrderDetails(orderDetailList);
//            return orderDto;
//        }).collect(Collectors.toList());
//
//        BeanUtils.copyProperties(pageInfo, pageDto, "records");
//        pageDto.setRecords(dtoList);
//        return R.success(pageDto);
        return R.success(pageInfo);
    }

}
