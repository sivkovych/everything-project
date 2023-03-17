package com.sivkovych.everything_project.domain;

import com.sivkovych.everything_project.domain.collection.Collection;
import com.sivkovych.everything_project.domain.collection.CollectionService;
import com.sivkovych.everything_project.domain.item.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfiguration {
    private final CollectionService collectionService;

    @PostConstruct
    @Transactional
    public void p() {
        var i1 = Item.builder()
                .data(Map.of("k1", "v1"))
                .build();
        var i2 = Item.builder()
                .data(Map.of("k2", "v2"))
                .build();
        var set = new HashSet<Item>();
        set.add(i1);
        set.add(i2);
        var c = Collection.builder()
                .items(set)
                .build();
        var s = collectionService.save(c);
        System.out.println("s = " + s);
        s.getItems()
                .add(Item.builder()
                             .data(Map.of("k3", "v3"))
                             .build());
        collectionService.save(c);
    }
}
