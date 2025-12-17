package com.web.demo.services;

import com.web.demo.models.KafkaLog;
import com.web.demo.repos.KafkaLogRepository;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class KafkaLogService {

    private final KafkaLogRepository repository;
    private final ProducerFactory<String, String> producerFactory;

    public KafkaLogService(KafkaLogRepository repository, ProducerFactory<String, String> producerFactory) {
        this.repository = repository;
        this.producerFactory = producerFactory;
    }

    public void logRequest(String messageId, String topic, String key, String payload, int keySize, int valueSize) {
        KafkaLog log = new KafkaLog();
        log.setMessageId(messageId);
        log.setTopic(topic);
        log.setKey(key);
        log.setPayload(payload);
        log.setStatus("REQUEST_SENT");
        log.setCreatedAt(LocalDateTime.now());
        if (keySize > 0) {
            log.setKeySize(keySize);
        }
        if (valueSize > 0) {
            log.setValueSize(valueSize);
        }

        repository.save(log);
    }

    public void logSuccess(String messageId, long offset, int partition, String topic) {
        KafkaLog log = repository.findById(messageId).orElseThrow();
        log.setOffsetValue(offset);
        log.setPartitionId(partition);
        log.setStatus("ACK_SUCCESS");
        log.setUpdatedAt(LocalDateTime.now());
        repository.save(log);

        // fetch partition info and update replicas
        try {
            PartitionInfo pi = getPartitionInfo(topic, partition);
            if (pi != null) {
                Node leader = pi.leader();
                if (leader != null) log.setLeaderNode(leader.id());

                String replicas = Arrays.stream(pi.replicas())
                        .map(n -> String.valueOf(n.id()))
                        .collect(Collectors.joining(","));
                log.setReplicas(replicas);

                String isr = Arrays.stream(pi.inSyncReplicas())
                        .map(n -> String.valueOf(n.id()))
                        .collect(Collectors.joining(","));
                log.setIsr(isr);

                repository.save(log);
            }
        } catch (Exception e) {
            // don't fail on metadata fetch
        }
    }

    public void logFailure(String messageId, String error) {
        KafkaLog log = repository.findById(messageId).orElseThrow();
        log.setStatus("ACK_FAILED: " + error);
        log.setUpdatedAt(LocalDateTime.now());
        repository.save(log);
    }

    private PartitionInfo getPartitionInfo(String topic, int partition) {
        try (Producer<String, String> producer = (Producer<String, String>) producerFactory.createProducer()) {
            java.util.List<PartitionInfo> list = producer.partitionsFor(topic);
            if (list == null) return null;
            return list.stream().filter(p -> p.partition() == partition).findFirst().orElse(null);
        }
    }
}