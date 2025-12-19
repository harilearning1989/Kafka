package com.web.demo.consumers;

import com.web.demo.dtos.OrderEvent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageProcessor {

    private final AuditService auditService;

    public OrderMessageProcessor(
            AuditService auditService) {
        this.auditService = auditService;
    }

    @Transactional
    public void process(
            ConsumerRecord<String, OrderEvent> record,
            Acknowledgment ack) {

        try {
            // 1️⃣ Save audit (RECEIVED)
            AuditContext auditCtx = auditService.auditReceived(record);

            // 3️⃣ Update audit (SUCCESS)
            auditService.auditSuccess(auditCtx);

            // 4️⃣ ACK only after DB commit
            ack.acknowledge();

        } catch (Exception ex) {
            auditService.auditFailure(record, ex);
            throw ex; // no ACK → retry
        }
    }
}

