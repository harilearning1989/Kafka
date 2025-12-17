package com.web.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "KAFKA_LOG")
@Data
public class KafkaLog {

    @Id
    @Column(name = "MESSAGE_ID", length = 64)
    private String messageId;

    private String topic;
    @Column(name = "message_key")
    private String key;

    @Lob
    @Column(name = "PAYLOAD", columnDefinition = "CLOB")
    private String payload;

    private String status;

    @Column(name = "PARTITION_ID")
    private Integer partitionId;

    @Column(name = "OFFSET_VALUE")
    private Long offsetValue;

    @Column(name = "LEADER_NODE")
    private Integer leaderNode;

    @Column(name = "REPLICAS")
    private String replicas; // comma separated

    @Column(name = "ISR")
    private String isr; // comma separated

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "KEY_SIZE")
    private Integer keySize;

    @Column(name = "VALUE_SIZE")
    private Integer valueSize;

}