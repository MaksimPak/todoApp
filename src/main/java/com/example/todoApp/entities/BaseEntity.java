package com.example.todoApp.entities;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Temporal(TIMESTAMP)
    protected Date createdDate;
    
	@LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastModifiedDate;
	
    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }
	

}
