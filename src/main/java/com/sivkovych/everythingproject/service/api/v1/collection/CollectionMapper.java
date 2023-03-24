package com.sivkovych.everythingproject.service.api.v1.collection;

import com.sivkovych.everythingproject.service.api.v1.collection.dto.GetCollection;
import com.sivkovych.everythingproject.service.api.v1.collection.dto.SetCollection;
import com.sivkovych.everythingproject.service.api.v1.item.ItemMapper;
import com.sivkovych.everythingproject.service.domain.collection.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring",
        uses = ItemMapper.class,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
interface CollectionMapper {
    @Mapping(target = "size",
             expression = "java(collection.getItems() == null ? 0 : collection.getItems().size())")
    GetCollection from(Collection collection);

    @Mapping(target = "id",
             source = "id")
    Collection to(Long id, SetCollection dto);

    Collection to(SetCollection dto);
}
