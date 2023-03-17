package com.sivkovych.everything_project.domain.item;

import com.sivkovych.everything_project.domain.MapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@ToString
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "collection_id",
            insertable = false,
            updatable = false,
            nullable = false)
    private Long collectionId;
    @Setter
    @ToString.Exclude
    @Convert(converter = MapConverter.class)
    @Column(name = "data",
            columnDefinition = "json")
    private Map<String, Object> data;
    @CreatedDate
    @Column(name = "created_at",
            nullable = false,
            updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Item(Map<String, Object> data) {
        this.data = data;
    }
}
