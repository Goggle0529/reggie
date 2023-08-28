package com.itheima.reggie.dto;

import com.itheima.reggie.entity.OrderDetail;
import lombok.Data;

import java.util.List;

/**
 * @author: Chenkai
 */
@Data
public class OrderDto {

    private List<OrderDetail> orderDetails;

}
