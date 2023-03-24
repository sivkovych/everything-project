package com.sivkovych.everythingproject.service.domain.item;

import com.sivkovych.everythingproject.service.domain.MapConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@ToString
@Table(name = "items")
@EntityListeners(AuditingEntityListener.class)
public class Item {
    @ToString.Exclude
    @Convert(converter = MapConverter.class)
    @Column(name = "data",
            columnDefinition = "json")
    private final Map<String, Object> data;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "collection_id",
            nullable = false)
    private Long collectionId;
    @CreatedDate
    @Column(name = "created_at",
            nullable = false,
            updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Item(Long id, Long collectionId, Map<String, Object> data) {
        this(data);
        this.id = id;
        this.collectionId = collectionId;
    }

    public Item(Map<String, Object> data) {
        this.data = data == null
                ? new HashMap<>()
                : data;
    }

    protected Item() {
        this(null);
    }

    public Set<Item> as() {
        var set = new HashSet<Item>();
        set.add(this);
        return set;
    }
}
