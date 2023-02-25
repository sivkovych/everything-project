package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.api.v1.RestControllerV1;
import com.iseedead.everything_project.api.v1.item.dto.CreateItem;
import com.iseedead.everything_project.api.v1.item.dto.GetItem;
import com.iseedead.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestControllerV1
@RequiredArgsConstructor
class ItemController {
    private final ItemService service;
    private final ItemMapper mapper;

    @GetMapping("/item/{id}")
    public GetItem getItem(@PathVariable Long id) throws ItemNotFound {
        return service.findBy(id)
                .map(mapper::itemToGetItem)
                .orElseThrow(ItemNotFound.get(id));
    }

    @PostMapping("/item")
    public Long addItem(CreateItem item) {
        return service.save(mapper.createItemToItem(item))
                .getId();
    }
}
