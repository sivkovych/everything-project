package com.sivkovych.everythingproject.service.api.v1.collection;

import com.sivkovych.everythingproject.service.api.HttpException;
import com.sivkovych.everythingproject.service.api.v1.RestControllerV1;
import com.sivkovych.everythingproject.service.api.v1.collection.dto.GetCollection;
import com.sivkovych.everythingproject.service.api.v1.collection.dto.SetCollection;
import com.sivkovych.everythingproject.service.domain.collection.Collection;
import com.sivkovych.everythingproject.service.domain.collection.CollectionService;
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
public class CollectionController {
    private final CollectionService service;
    private final CollectionMapper mapper;

    @GetMapping("/collections/{id}")
    public GetCollection get(@PathVariable Long id) throws HttpException {
        return service.findBy(id)
                .map(mapper::from)
                .orElseThrow(CollectionNotFound.get(id));
    }

    @PostMapping(path = "/collections",
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public GetCollection create(@RequestBody Optional<SetCollection> collection) throws HttpException {
        return collection.map(mapper::to)
                .map(this::save)
                .orElseThrow(getException(collection));
    }

    @PutMapping(path = "/collections/{id}",
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public GetCollection update(@PathVariable Long id, @RequestBody Optional<SetCollection> collection)
            throws HttpException {
        return collection.map(dto -> mapper.to(id, dto))
                .map(this::save)
                .orElseThrow(getException(collection));
    }

    @DeleteMapping("/collections/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private GetCollection save(Collection collection) {
        return mapper.from(service.save(collection));
    }
}
