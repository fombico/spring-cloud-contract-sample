package com.fombico.producer;

import com.fombico.producer.exceptions.OrderNotFoundException;
import com.fombico.producer.messaging.Messaging;
import com.fombico.producer.models.Order;
import com.fombico.producer.models.OrderRequest;
import com.fombico.producer.models.OrderVerifiedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private Map<String, Order> orders = new HashMap<>();
    private Messaging messaging;
    private AppDateTime appDateTime;

    public OrderService(Messaging messaging,
                        AppDateTime appDateTime) {
        this.messaging = messaging;
        this.appDateTime = appDateTime;
    }

    public Order getOrder(String orderId) {
        log.info("Looking for orderId {}", orderId);
        return orders.computeIfAbsent(orderId, id -> {
            throw new OrderNotFoundException();
        });
    }

    public Order createOrder(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();
        Order order = Order.builder()
                .orderId(orderId)
                .createdAt(LocalDateTime.now().toString())
                .createdBy(orderRequest.getCreatedBy())
                .build();
        orders.put(orderId, order);
        log.info("Created orderId {} for {}", orderId, orderRequest.getCreatedBy());
        publishOrderVerifiedEvent(orderId);
        return order;
    }

    private void publishOrderVerifiedEvent(String orderId) {
        OrderVerifiedEvent orderVerifiedEvent = OrderVerifiedEvent.builder()
                .orderId(orderId)
                .eventDateTime(appDateTime.getCurrentYearToMillisecondDateTime())
                .eventName(Messaging.ORDER_VERIFIED_EVENT_TYPE)
                .build();
        Message<OrderVerifiedEvent> message = MessageBuilder.withPayload(orderVerifiedEvent).build();
        log.info("Publishing order verified event with orderId {}", orderId);
        messaging.getOrderRequestVerifiedEventsChannel().send(message);
    }
}
