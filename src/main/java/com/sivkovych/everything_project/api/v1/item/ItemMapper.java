package com.sivkovych.everything_project.api.v1.item;

import com.sivkovych.everything_project.api.v1.item.dto.GetItem;
import com.sivkovych.everything_project.api.v1.item.dto.SetItem;
import com.sivkovych.everything_project.domain.item.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ItemMapper {
    GetItem from(Item item);

    Item to(SetItem dto);

    Item to(Long id, SetItem dto);
}
