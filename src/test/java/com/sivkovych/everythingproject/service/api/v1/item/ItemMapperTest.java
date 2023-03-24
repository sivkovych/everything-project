package com.sivkovych.everythingproject.service.api.v1.item;

import com.sivkovych.everythingproject._util.data.BiData;
import com.sivkovych.everythingproject._util.data.GroupPentaData;
import com.sivkovych.everythingproject._util.data.MonoData;
import com.sivkovych.everythingproject._util.data.TriData;
import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.api.v1.item.dto.GetItem;
import com.sivkovych.everythingproject.service.api.v1.item.dto.SetItem;
import com.sivkovych.everythingproject.service.domain.item.Item;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ForClass(ItemMapper.class)
class ItemMapperTest {
    private static final Map<String, Object> JSON_DATA = Map.of("nested-key", Map.of("key", "value"));
    private static final LocalDateTime NOW = LocalDateTime.now();
    private final ItemMapper mapper = Mappers.getMapper(ItemMapper.class);

    @TestFactory
    @ForMethod("from(Item item)")
    public Stream<DynamicTest> shouldConvertFromItemToGetItem() {
        return dataForFrom().map(data -> dynamicTest(data.text(), () -> {
            var getItem = mapper.from(data.a());
            assertNotNull(getItem);
            assertThat(getItem, samePropertyValuesAs(data.expected()));
        }));
    }

    @TestFactory
    @ForMethod("to(Long collectionId, SetItem dto)")
    public Stream<DynamicTest> shouldConvertToItemWithCollectionIdAndSetItem() {
        return dataForToWithoutId().map(data -> dynamicTest(data.text(), () -> {
            var item = mapper.to(data.a(), data.b());
            assertNotNull(item);
            assertThat(item, samePropertyValuesAs(data.expected()));
        }));
    }

    @TestFactory
    @ForMethod("to(Long collectionId, Long id, SetItem dto)")
    public Stream<DynamicTest> shouldConvertToItemWithItemIdAndCollectionAndSetItem() {
        return dataForToWithId().map(data -> dynamicTest(data.text(), () -> {
            var item = mapper.to(data.b(), data.a(), data.c());
            assertNotNull(item);
            assertThat(item, samePropertyValuesAs(data.expected()));
        }));
    }

    private Stream<BiData<Long, SetItem, Item>> dataForToWithoutId() {
        return BiData.stream((a, b) -> new Item(null, a, b == null
                ? null
                : b.data()), (a, b, e) -> format("[collectionId='%s', setItem-size='%s']", a, b == null
                ? null
                : b.data()
                        .size()), 2L, new SetItem(JSON_DATA));
    }

    private Stream<TriData<Long, Long, SetItem, Item>> dataForToWithId() {
        return TriData.stream((a, b, c) -> new Item(a, b, c == null
                ? null
                : c.data()), (a, b, c, e) -> format("[id='%s', collectionId='%s', setItem-size='%s']", a, b, c == null
                ? null
                : c.data()
                        .size()), 1L, 2L, new SetItem(JSON_DATA));
    }

    private Stream<MonoData<Item, GetItem>> dataForFrom() {
        return GroupPentaData.stream(GetItem::new, (a, b, c, d, e, expected) -> format(
                "[id='%s', collectionId='%s', data-size='%s', createdAt='%s', " + "updatedAt='%s']", a, b, c == null
                        ? null
                        : c.size(), d, e), (a, b, c, d, e) -> {
            var item = new Item(a, b, c);
            ReflectionTestUtils.setField(item, "createdAt", d);
            ReflectionTestUtils.setField(item, "updatedAt", e);
            return item;
        }, 1L, 2L, JSON_DATA, NOW, NOW);
    }
}
