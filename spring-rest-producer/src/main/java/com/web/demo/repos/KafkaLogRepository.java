package com.web.demo.repos;

import com.web.demo.models.KafkaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaLogRepository extends JpaRepository<KafkaLog, String> {
}
