package com.sivkovych.everythingproject.service.api.v1.item;

import com.sivkovych.everythingproject.service.api.HttpException;
import com.sivkovych.everythingproject.service.api.v1.RestControllerV1;
import com.sivkovych.everythingproject.service.api.v1.item.dto.GetItem;
import com.sivkovych.everythingproject.service.api.v1.item.dto.SetItem;
import com.sivkovych.everythingproject.service.domain.item.Item;
import com.sivkovych.everythingproject.service.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import static com.sivkovych.everythingproject.service.api.ApiExceptionHandler.getException;

@RequiredArgsConstructor
@RestControllerV1(produces = MediaType.APPLICATION_JSON_VALUE)
class ItemController {
    private final ItemService service;
    private final ItemMapper mapper;

    @GetMapping("/collections/{collectionId}/items/{itemId}")
    public GetItem get(@PathVariable Long collectionId, @PathVariable Long itemId) throws HttpException {
        return service.findBy(collectionId, itemId)
                .map(mapper::from)
                .orElseThrow(ItemNotFound.get(collectionId, itemId));
    }

    @PostMapping(path = "/collections/{collectionId}/items",
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public GetItem create(@PathVariable Long collectionId, @RequestBody Optional<SetItem> item) throws HttpException {
        return item.map(retrieved -> mapper.to(collectionId, retrieved))
                .map(this::save)
                .orElseThrow(getException(item));
    }

    @PutMapping(path = "/collections/{collectionId}/items/{itemId}",
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public GetItem update(@PathVariable Long collectionId,
                          @PathVariable Long itemId,
                          @RequestBody Optional<SetItem> item) throws HttpException {
        return item.map(dto -> mapper.to(collectionId, itemId, dto))
                .map(this::save)
                .orElseThrow(getException(item));
    }

    @DeleteMapping("/collections/{collectionId}/items/{itemId}")
    public void delete(@PathVariable Long collectionId, @PathVariable Long itemId) {
        service.delete(collectionId, itemId);
    }

    private GetItem save(Item item) {
        return mapper.from(service.save(item));
    }
}
