package contracts.producer.rest.orders

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description'POST /v1/orders creates order with createdBy field'
    request {
        method 'POST'
        url '/v1/orders'
        body(
                createdBy: "Peter Parker"
        )
        headers {
            contentType applicationJson()
        }
    }
    response {
        status 200

        body(
                createdBy: fromRequest().body('$.createdBy'),
                createdAt: $(anyIso8601WithOffset()),
                orderId: $(anyUuid())
        )
        headers {
            contentType applicationJson()
        }
    }
}
