package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.api.v1.HttpException;
import com.iseedead.everything_project.api.v1.RestControllerV1;
import com.iseedead.everything_project.api.v1.item.dto.CreateItem;
import com.iseedead.everything_project.api.v1.item.dto.GetItem;
import com.iseedead.everything_project.domain.item.Item;
import com.iseedead.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RestControllerV1
@RequiredArgsConstructor
class ItemController {
    private final ItemService service;
    private final ItemMapper mapper;

    @GetMapping("/item/{id}")
    public GetItem get(@PathVariable Long id) throws HttpException {
        return service.findBy(id)
                .map(mapper::from)
                .orElseThrow(ItemNotFound.get(id));
    }

    @PostMapping(value = "/item")
    public Long set(@RequestBody Optional<CreateItem> item) throws HttpException {
        return item.map(mapper::to)
                .map(service::save)
                .map(Item::getId)
                .orElseThrow(HttpException.get(HttpStatus.BAD_REQUEST, item.isEmpty()
                        ? "No data passed"
                        : "Invalid data passed"));
    }

    @DeleteMapping("/item/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
