package com.iseedead.everything_project.api.v1.item;

import com.iseedead.everything_project.api.v1.item.dto.CreateItem;
import com.iseedead.everything_project.api.v1.item.dto.GetItem;
import com.iseedead.everything_project.domain.item.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ItemMapper {
    GetItem itemToGetItem(Item item);

    Item createItemToItem(CreateItem createItem);
}
