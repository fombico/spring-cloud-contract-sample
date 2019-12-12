package com.fombico.consumer;

import com.fombico.consumer.models.Order;
import com.fombico.consumer.models.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ProducerClient {

    private final String host;

    public ProducerClient(@Value("${producer.host}") String host) {
        this.host = host;
    }

    public Order createOrder(String name) {
        OrderRequest orderRequest = OrderRequest.builder()
                .createdBy(name)
                .build();
        Order order = new RestTemplate().postForObject(host + "/v1/orders", orderRequest, Order.class);
        log.info("Order created with orderId {}", order.getOrderId());
        return order;
    }
}
