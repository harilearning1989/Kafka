package com.web.demo.consumers;

public class AuditContext {

    private final Long auditId;

    public AuditContext(Long auditId) {
        this.auditId = auditId;
    }

    public Long getAuditId() {
        return auditId;
    }
}

