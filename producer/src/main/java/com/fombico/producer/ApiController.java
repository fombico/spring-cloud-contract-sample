package com.fombico.producer;

import com.fombico.producer.models.Order;
import com.fombico.producer.models.OrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ApiController {

    private OrderService orderService;

    @GetMapping("/v1/orders/{order-id}")
    public Order getOrder(@PathVariable("order-id") String orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping("/v1/orders")
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }
}
