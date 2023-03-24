package com.sivkovych.everythingproject.service.domain.collection;

import com.sivkovych.everythingproject.service.domain.item.Item;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@ToString
@Table(name = "collections")
@EntityListeners(AuditingEntityListener.class)
public class Collection {
    @ToString.Exclude
    @OneToMany(mappedBy = "collectionId",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL)
    private final Set<Item> items;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @CreatedDate
    @Column(name = "created_at",
            nullable = false,
            updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Collection(Long id, String name, Set<Item> items) {
        this(name, items);
        this.id = id;
    }

    public Collection(String name, Set<Item> items) {
        this(items);
        this.name = name;
    }

    public Collection(Set<Item> items) {
        this.items = items == null
                ? new HashSet<>()
                : items;
    }

    protected Collection() {
        this(null);
    }
}
