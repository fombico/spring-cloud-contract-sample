package com.fombico.producer;

import com.fombico.producer.messaging.Messaging;
import com.fombico.producer.models.OrderVerifiedEvent;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.fombico.producer.TestUtils.getCurrentDateTime;

@SpringBootTest(classes = ProducerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
@ExtendWith(SpringExtension.class)
public abstract class MessagingBase {

    @Autowired
    Messaging messaging;

    protected void publishOrderVerifiedEvent() {
        OrderVerifiedEvent orderVerifiedEvent = OrderVerifiedEvent.builder()
                .eventName(Messaging.ORDER_VERIFIED_EVENT_TYPE)
                .orderId(UUID.randomUUID().toString())
                .eventDateTime(getCurrentDateTime())
                .build();
        Message<OrderVerifiedEvent> message = MessageBuilder.withPayload(orderVerifiedEvent).build();
        messaging.getOrderRequestVerifiedEventsChannel().send(message);
    }
}
