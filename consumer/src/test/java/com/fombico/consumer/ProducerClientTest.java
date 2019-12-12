package com.fombico.consumer;

import com.fombico.consumer.models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cloud.contract.spec.internal.RegexPatterns;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureStubRunner(ids = "com.fombico:producer", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@ExtendWith(SpringExtension.class)
class ProducerClientTest {

    private ProducerClient producerClient;

    @StubRunnerPort("com.fombico:producer")
    int stubPort;

    @BeforeEach
    void setup() {
        producerClient = new ProducerClient("http://localhost:" + stubPort);
    }

    @Nested
    @DisplayName("Create Order")
    class CreateOrder {

        @Test
        @DisplayName("returns order")
        void returnsOrder() {
            Order order = producerClient.createOrder("Peter Parker");
            assertThat(order).isNotNull();
            assertThat(order.getCreatedAt()).matches(RegexPatterns.iso8601WithOffset().pattern());
            assertThat(order.getCreatedBy()).isEqualTo("Peter Parker");
            assertThat(order.getOrderId()).matches(RegexPatterns.uuid().pattern());
        }
    }
}