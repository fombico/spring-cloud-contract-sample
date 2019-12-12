package contracts.producer.messaging.async

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'publishes order verified event when an order is created'
    input {
        triggeredBy('publishOrderVerifiedEvent()')
    }
    outputMessage {
        sentTo 'order-verified-events'
        body([
                eventName    : 'order-verified',
                eventDateTime: $(anyIso8601WithOffset()),
                orderId      : $(anyUuid())
        ])
    }
}
