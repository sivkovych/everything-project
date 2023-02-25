package com.iseedead.everything_project.domain.item;

import com.iseedead.everything_project.domain.MapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = MapConverter.class)
    @Column(name = "data",
            columnDefinition = "json")
    private Map<String, Object> data;
    @CreatedDate
    @Column(name = "created_at",
            nullable = false,
            updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedBy
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}