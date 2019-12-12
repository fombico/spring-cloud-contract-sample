package com.fombico.producer;

import com.fombico.producer.exceptions.OrderNotFoundException;
import com.fombico.producer.models.Order;
import com.fombico.producer.models.OrderRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.UUID;

import static com.fombico.producer.TestUtils.getCurrentDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public abstract class RestBase {

    private static final String ORDER_ID_NOT_FOUND = "order-id-not-found";

    @Mock
    OrderService orderService;

    @InjectMocks
    private ApiController apiController;

    @BeforeEach
    void setup() {
        when(orderService.getOrder(anyString())).thenAnswer(invocationOnMock -> Order.builder()
                .orderId(invocationOnMock.getArgument(0))
                .createdBy("Fred")
                .createdAt(getCurrentDateTime())
                .build());

        when(orderService.getOrder(ORDER_ID_NOT_FOUND)).thenThrow(new OrderNotFoundException());

        when(orderService.createOrder(any(OrderRequest.class))).thenAnswer(invocationOnMock -> {
            OrderRequest orderRequest = invocationOnMock.getArgument(0);
            return Order.builder()
                    .createdBy(orderRequest.getCreatedBy())
                    .createdAt(getCurrentDateTime())
                    .orderId(UUID.randomUUID().toString())
                    .build();
        });

        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(apiController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}
