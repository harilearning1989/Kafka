package com.web.demo.repos;


import com.web.demo.consumers.KafkaMessageAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaMessageAuditRepository
        extends JpaRepository<KafkaMessageAudit, Long> {

    @Modifying
    @Query("""
        UPDATE KafkaMessageAudit a
        SET a.status = :status,
            a.errorMessage = :errorMessage
        WHERE a.id = :id
    """)
    int updateStatus(
            @Param("id") Long id,
            @Param("status") String status,
            @Param("errorMessage") String errorMessage);
}
