package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.api.v1.item.dto.SetItem;
import com.iseedead.everything_project.api.v1.item.dto.GetItem;
import com.iseedead.everything_project.domain.item.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ItemMapper {
    GetItem from(Item item);

    Item to(SetItem dto);

    Item to(Long id, SetItem dto);
}
