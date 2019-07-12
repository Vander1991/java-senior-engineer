package szu.vander.sharding.jdbc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szu.vander.sharding.jdbc.model.Order;
import szu.vander.sharding.jdbc.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("add")
    public Order addOrder(Order o) {
        this.orderService.addOrder(o);
        return o;
    }

    @RequestMapping("get")
    public Order getOrder(Long orderId) {
        return this.orderService.getOrder(orderId);
    }
}
