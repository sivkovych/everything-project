package com.iseedead.everything_project.domain.system_property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_properties")
@EntityListeners(AuditingEntityListener.class)
public class SystemProperty {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "name",
            unique = true)
    private SPName name;
    @Column(name = "description")
    private String description;
    @LastModifiedBy
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
