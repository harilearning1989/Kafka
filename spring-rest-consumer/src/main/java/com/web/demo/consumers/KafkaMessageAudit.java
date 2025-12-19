package com.web.demo.consumers;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "kafka_message_audit")
@Data
public class KafkaMessageAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageKey;
    private String topic;
    private int partitionNo;
    private long offsetNo;

    @Column(columnDefinition = "jsonb")
    private String payload;

    private String status;
    private String errorMessage;

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters & setters


}

