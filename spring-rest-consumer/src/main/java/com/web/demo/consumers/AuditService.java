package com.web.demo.consumers;

import com.web.demo.repos.KafkaMessageAuditRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class AuditService {

    private final KafkaMessageAuditRepository repo;
    private final ObjectMapper mapper;

    public AuditService(
            KafkaMessageAuditRepository repo,
            ObjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public AuditContext auditReceived(ConsumerRecord<?, ?> record) {
        KafkaMessageAudit audit = new KafkaMessageAudit();
        audit.setMessageKey(String.valueOf(record.key()));
        audit.setTopic(record.topic());
        audit.setPartitionNo(record.partition());
        audit.setOffsetNo(record.offset());
        audit.setStatus("RECEIVED");
        audit.setPayload(toJson(record.value()));

        repo.save(audit);
        return new AuditContext(audit.getId());
    }

    public void auditSuccess(AuditContext ctx) {
        repo.updateStatus(ctx.getAuditId(), "SUCCESS", null);
    }

    public void auditFailure(ConsumerRecord<?, ?> record, Exception ex) {
        KafkaMessageAudit audit = new KafkaMessageAudit();
        audit.setMessageKey(String.valueOf(record.key()));
        audit.setTopic(record.topic());
        audit.setPartitionNo(record.partition());
        audit.setOffsetNo(record.offset());
        audit.setStatus("FAILED");
        audit.setErrorMessage(ex.getMessage());

        repo.save(audit);
    }

    private String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            return "{}";
        }
    }
}

