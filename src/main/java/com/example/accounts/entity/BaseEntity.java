package com.example.accounts.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(insertable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Column(updatable = false)
    private String createdBy;
    @Column(insertable = false)
    private String updatedBy;

}
