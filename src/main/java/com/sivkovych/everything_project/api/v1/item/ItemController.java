package com.sivkovych.everything_project.api.v1.item;

import com.sivkovych.everything_project.api.HttpException;
import com.sivkovych.everything_project.api.v1.RestControllerV1;
import com.sivkovych.everything_project.api.v1.item.dto.GetItem;
import com.sivkovych.everything_project.api.v1.item.dto.SetItem;
import com.sivkovych.everything_project.domain.item.Item;
import com.sivkovych.everything_project.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@RestControllerV1(produces = MediaType.APPLICATION_JSON_VALUE)
class ItemController {
    private final ItemService service;
    private final ItemMapper mapper;

    @GetMapping("/items/{id}")
    public GetItem get(@PathVariable Long id) throws HttpException {
        return service.findBy(id)
                .map(mapper::from)
                .orElseThrow(ItemNotFound.get(id));
    }

    @PostMapping(path = "/items",
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public GetItem create(@RequestBody Optional<SetItem> item) throws HttpException {
        return item.map(mapper::to)
                .map(this::save)
                .orElseThrow(getException(item));
    }

    @PutMapping(path = "/items/{id}",
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public GetItem update(@PathVariable Long id, @RequestBody Optional<SetItem> item) throws HttpException {
        return item.map(dto -> mapper.to(id, dto))
                .map(this::save)
                .orElseThrow(getException(item));
    }

    @DeleteMapping("/items/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private GetItem save(Item item) {
        return mapper.from(service.save(item));
    }

    private <T> Supplier<HttpException> getException(Optional<T> optional) {
        return HttpException.get(HttpStatus.BAD_REQUEST, optional.isEmpty()
                ? "No data passed"
                : "Invalid data passed");
    }
}
