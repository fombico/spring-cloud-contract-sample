package com.fombico.producer.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Messaging {
    String ORDER_VERIFIED_EVENTS = "order-verified-events";
    String ORDER_VERIFIED_EVENT_TYPE = "order-verified";

    @Output(ORDER_VERIFIED_EVENTS)
    MessageChannel getOrderRequestVerifiedEventsChannel();
}
