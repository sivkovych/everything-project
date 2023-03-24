package com.sivkovych.everythingproject.service.api.v1.item;

import com.sivkovych.everythingproject.service.api.v1.item.dto.GetItem;
import com.sivkovych.everythingproject.service.api.v1.item.dto.SetItem;
import com.sivkovych.everythingproject.service.domain.item.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ItemMapper {
    GetItem from(Item item);

    @Mapping(target = "collectionId",
             source = "collectionId")
    @Mapping(target = "id",
             ignore = true)
    Item to(Long collectionId, SetItem dto);

    @Mapping(target = "collectionId",
             source = "collectionId")
    @Mapping(target = "id",
             source = "id")
    Item to(Long collectionId, Long id, SetItem dto);
}
