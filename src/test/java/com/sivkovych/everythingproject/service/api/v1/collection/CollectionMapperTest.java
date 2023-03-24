package com.sivkovych.everythingproject.service.api.v1.collection;

import com.sivkovych.everythingproject._util.data.BiData;
import com.sivkovych.everythingproject._util.data.GroupPentaData;
import com.sivkovych.everythingproject._util.data.MonoData;
import com.sivkovych.everythingproject._util.displayname.ForClass;
import com.sivkovych.everythingproject._util.displayname.ForMethod;
import com.sivkovych.everythingproject.service.api.v1.collection.dto.GetCollection;
import com.sivkovych.everythingproject.service.api.v1.collection.dto.SetCollection;
import com.sivkovych.everythingproject.service.api.v1.item.ItemMapper;
import com.sivkovych.everythingproject.service.domain.collection.Collection;
import com.sivkovych.everythingproject.service.domain.item.Item;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ForClass(CollectionMapper.class)
class CollectionMapperTest {
    private static final Map<String, Object> JSON_DATA = Map.of("nested-key", Map.of("key", "value"));
    private static final LocalDateTime NOW = LocalDateTime.now();
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private final CollectionMapper mapper = Mappers.getMapper(CollectionMapper.class);

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(mapper, "itemMapper", itemMapper);
    }

    @TestFactory
    @ForMethod("from(Collection collection)")
    public Stream<DynamicTest> shouldConvertFromCollectionToGetCollection() {
        return dataForFrom().map(data -> dynamicTest(data.text(), () -> {
            var getCollection = mapper.from(data.a());
            assertNotNull(getCollection);
            MatcherAssert.assertThat(getCollection, equalTo(data.expected()));
        }));
    }

    @TestFactory
    @ForMethod("to(SetCollection dto)")
    public Stream<DynamicTest> shouldConvertToCollectionWithSetCollection() {
        return dataForToWithoutId().map(data -> dynamicTest(data.text(), () -> {
            var collection = mapper.to(data.a());
            assertNotNull(collection);
            MatcherAssert.assertThat(collection, samePropertyValuesAs(data.expected()));
        }));
    }

    @TestFactory
    @ForMethod("to(Long collectionId, Long id, SetItem dto)")
    public Stream<DynamicTest> shouldConvertToCollectionWithCollectionIdAndSetCollection() {
        return dataForToWithId().map(data -> dynamicTest(data.text(), () -> {
            var collection = mapper.to(data.a(), data.b());
            assertNotNull(collection);
            assertThat(collection, samePropertyValuesAs(data.expected()));
        }));
    }

    private Stream<MonoData<SetCollection, Collection>> dataForToWithoutId() {
        return MonoData.stream(a -> new Collection(a == null
                                                           ? null
                                                           : a.name(), null), (a, e) -> format("[name='%s']", a),
                               new SetCollection("some-name"));
    }

    private Stream<BiData<Long, SetCollection, Collection>> dataForToWithId() {
        return BiData.stream((a, b) -> new Collection(a, b == null
                                     ? null
                                     : b.name(), null), (a, b, e) -> format("[id='%s', name='%s']", a, b), 1L,
                             new SetCollection("some-name"));
    }

    private Stream<MonoData<Collection, GetCollection>> dataForFrom() {
        return GroupPentaData.stream((a, b, c, d, e) -> new GetCollection(a, b, c == null
                ? null
                : c.stream()
                        .map(itemMapper::from)
                        .collect(Collectors.toSet()), d, e), (a, b, c, d, e, expected) -> format(
                "[id='%s', name='%s', data-size='%s', createdAt='%s', " + "updatedAt='%s']", a, b, c == null
                        ? null
                        : c.size(), d, e), (a, b, c, d, e) -> {
            var collection = new Collection(a, b, c);
            ReflectionTestUtils.setField(collection, "createdAt", d);
            ReflectionTestUtils.setField(collection, "updatedAt", e);
            return collection;
        }, 1L, "some-collection-name", Set.of(new Item(JSON_DATA)), NOW, NOW);
    }
}
