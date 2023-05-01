package com.example.controller;

import com.example.common.R;
import com.example.entity.Orders;
import com.example.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

   /**
    * 用户下单
    * @author H2
    * @date  18:33
    * @param orders
    * @return R<String>
    */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        return null;
    }
}
