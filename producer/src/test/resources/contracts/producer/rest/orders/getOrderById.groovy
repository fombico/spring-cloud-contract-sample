package contracts.producer.rest.orders

import org.springframework.cloud.contract.spec.Contract

[
        Contract.make {
            description'GET /v1/orders/{order-id} returns 200 and order if orderId found'
            request {
                method 'GET'
                url '/v1/orders/' + client(UUID.randomUUID().toString()).getClientValue()
            }
            response {
                status 200
                body(
                        createdBy: "Fred",
                        createdAt: $(anyIso8601WithOffset()),
                        orderId: fromRequest().path(2)
                )
                bodyMatchers {
                    jsonPath('$.orderId', byRegex(uuid()))
                    jsonPath('$.orderId', byEquality())
                }
            }
        },
        Contract.make {
            description 'GET /v1/orders/{order-id} returns 404 if orderId not found'
            request {
                method 'GET'
                url '/v1/orders/order-id-not-found'
            }
            response {
                status 404
            }
        }
]