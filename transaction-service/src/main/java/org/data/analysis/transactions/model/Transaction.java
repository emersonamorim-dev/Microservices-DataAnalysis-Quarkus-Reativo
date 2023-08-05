package org.data.analysis.transactions.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction extends PanacheEntity {
    public Long accountId;
    public BigDecimal amount;
    public LocalDateTime timestamp;
}

