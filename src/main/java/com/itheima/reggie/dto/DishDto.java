package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 注意，父类Dish中属性都是private，这里可以继承的原因是：
 * @Data注解
 * 自动生成set和get方法，所以继承的是set和get，通过这种方法调用父类的属性。
 */
//@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
