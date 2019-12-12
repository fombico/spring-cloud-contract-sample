package com.fombico.producer.config;

import com.fombico.producer.messaging.Messaging;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(Messaging.class)
public class RabbitConfig {
}
