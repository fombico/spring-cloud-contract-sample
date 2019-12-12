package com.fombico.producer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderVerifiedEvent {
    private String eventName;
    private String eventDateTime;
    private String orderId;
}
